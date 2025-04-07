import { useAuth } from "@/stores/auth";
import { WS_BASE_URL } from "@/types";
import { Client } from "@stomp/stompjs";
import { onMounted, onUnmounted, ref } from "vue";

interface WebSocketSubscription {
    topic: string;
    callback: (message: any) => void;
    subscription: { unsubscribe: () => void } | null;
}

export const useWebSocket = () => {
    const auth = useAuth();
    const subscriptions = ref<WebSocketSubscription[]>([]);

    const client = new Client({
        brokerURL: auth.token
            ? `${WS_BASE_URL}/ws?token=${auth.rawToken}`
            : `${WS_BASE_URL}/ws`,
        debug: (str) => {
            console.log(str);
        },
        onStompError: (frame) => {
            console.error("Broker reported error: " + frame.headers["message"]);
            console.error("Additional details: " + frame.body);
        },
    });

    client.onConnect = () => {
        // Process queued subscriptions now that we're connected
        subscriptions.value.forEach((sub) => {
            if (!sub.subscription) {
                sub.subscription = client.subscribe(sub.topic, (message) => {
                    sub.callback(JSON.parse(message.body));
                });
            }
        });
    };

    onMounted(() => {
        client.activate();
    });

    const disconnect = () => {
        client.deactivate();
    };

    onUnmounted(() => {
        disconnect();
    });

    const subscribe = (topic: string, callback: (message: any) => void) => {
        const sub: WebSocketSubscription = { topic, callback, subscription: null };
        // If already connected, subscribe immediately
        if (client.active) {
            sub.subscription = client.subscribe(topic, (message) => {
                callback(JSON.parse(message.body));
            });
        }
        subscriptions.value.push(sub);

        // Return an unsubscribe function
        return () => {
            if (sub.subscription) {
                sub.subscription.unsubscribe();
            }
            subscriptions.value = subscriptions.value.filter(s => s !== sub);
        };
    };

    return {
        subscribe,
        disconnect,
    }
}