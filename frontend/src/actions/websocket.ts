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

/**
 * Subscribes to a topic and registers a callback to be called when a message is received.
 * @param topic The topic to subscribe to.
 * @param callback The callback to be called when a message is received.
 */
const subscribe = (topic: string, callback: (message: any) => void) => {
    // If the client is not initialized, throw
    if (!client.value) {
        throw new Error("WebSocket client is not initialized");
    }

    // If the client is not connected, queue the subscription for when it connects
    if (!client.value.connected) {
        queuedSubscriptions.value.push({
            topic,
            callback,
            subscription: null,
        });
    } else {
        // If the client is connected, subscribe to the topic immediately
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

    // If the user is not logged in, we cannot use the WebSocket client
    if (!auth.isLoggedIn()) {
        throw new Error("Cannot use WebSocket without being logged in. This breaks useWebsocket.");
    }

    // If the client is already initialized, create it
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

/**
 * Creates a new WebSocket client and connects to the server with the given token 
 * and all queued subscriptions.
 */
const createClient = () => {
    const auth = useAuth();

    // Create a new STOMP client
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
            // Log errors if they ocur
            console.error("Broker reported error: " + frame.headers["message"]);
            console.error("Additional details: " + frame.body);
        },
    });

    client.value.activate();
}