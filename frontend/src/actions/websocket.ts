import { useAuth } from "@/stores/auth";
import { WS_BASE_URL } from "@/types";
import { Client } from "@stomp/stompjs";
import { storeToRefs } from "pinia";
import { onMounted, onUnmounted, ref, watch } from "vue";

interface WebSocketSubscription {
    topic: string;
    callback: (message: any) => void;
    subscription: { unsubscribe: () => void } | null;
}

const client = ref<Client | null>(null);
const subscriptions = ref<WebSocketSubscription[]>([]);
const queuedSubscriptions = ref<WebSocketSubscription[]>([]);

const subscribe = (topic: string, callback: (message: any) => void) => {
    if (!client.value) {
        throw new Error("WebSocket client is not initialized");
    }

    if (!client.value.connected) {
        queuedSubscriptions.value.push({
            topic,
            callback,
            subscription: null,
        });
    } else {
        const subscription = client.value.subscribe(topic, (message) => {
            callback(JSON.parse(message.body));
        });
        subscriptions.value.push({
            topic,
            callback,
            subscription,
        });
    }

    // Unsubscribe from the topic when the component is unmounted
    onUnmounted(() => {
        const index = subscriptions.value.findIndex((sub) => sub.topic === topic);
        if (index !== -1) {
            subscriptions.value[index].subscription?.unsubscribe();
            subscriptions.value.splice(index, 1);
        }
    });
};

/**
 * Hook for using a Websocket client. Initializes the client if it is not already initialized.
 * 
 * @returns an object with a subscribe function that can be used to subscribe to topics.
 */
export const useWebSocket = () => {
    const auth = useAuth();

    if (!auth.isLoggedIn()) {
        throw new Error("Cannot use WebSocket without being logged in. This breaks useWebsocket.");
    }

    if (!client.value) {
        createClient();
    }

    // When unmounting, it could be that the user logged out.
    // In that case, we need to deactivate the client.
    onUnmounted(() => {
        if (!auth.isLoggedIn()) {
            client.value?.deactivate();
            client.value = null;
        }
    })

    return {
        subscribe
    };
}

const createClient = () => {
    const auth = useAuth();

    client.value = new Client({
        brokerURL: auth.token
            ? `${WS_BASE_URL}/ws?token=${auth.rawToken}`
            : `${WS_BASE_URL}/ws`,
        onConnect: () => {
            if (!client.value) {
                throw new Error("Client is null in onConnect");
            }

            // Subscribe to all queued subscriptions
            queuedSubscriptions.value.forEach((sub) => {
                const subscription = client.value!.subscribe(sub.topic, (message) => {
                    sub.callback(JSON.parse(message.body));
                });
                subscriptions.value.push({
                    ...sub,
                    subscription,
                });
            });
            queuedSubscriptions.value = [];
        },
        onStompError: (frame) => {
            console.error("Broker reported error: " + frame.headers["message"]);
            console.error("Additional details: " + frame.body);
        },
    });

    client.value.activate();
}