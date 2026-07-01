<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Firebase {
    private $server_key;
    private $project_id;
    private $CI;

    public function __construct() {
        $this->CI =& get_instance();
        $this->server_key = 'AIza_REDACTED_API_KEY';
        // Tambahkan Project ID dari Firebase Console
        $this->project_id = 'ogot-projek';
    }

    /**
     * Fungsi untuk mengirim notifikasi ke single device
     */
    public function send_notification($token, $title, $body, $data = array()) {
        $message = array(
            'message' => array(
                'token' => $token,
                'notification' => array(
                    'title' => $title,
                    'body' => $body
                ),
                'data' => $data
            )
        );

        return $this->send($message);
    }

    /**
     * Fungsi untuk mengirim notifikasi ke multiple devices
     */
    public function send_multiple_notification($tokens, $title, $body, $data = array()) {
        $message = array(
            'message' => array(
                'tokens' => $tokens,
                'notification' => array(
                    'title' => $title,
                    'body' => $body
                ),
                'data' => $data
            )
        );

        return $this->send($message);
    }

    /**
     * Fungsi untuk mengirim notifikasi ke topic
     */
    public function send_topic_notification($topic, $title, $body, $data = array()) {
        $message = array(
            'message' => array(
                'topic' => $topic,
                'notification' => array(
                    'title' => $title,
                    'body' => $body
                ),
                'data' => $data
            )
        );

        return $this->send($message);
    }

    /**
     * Fungsi private untuk mendapatkan access token
     */
    private function get_access_token() {
        // Gunakan Google Service Account credentials
        $credentials_path = APPPATH . 'config/ogot-projek-firebase-adminsdk-h3jsg-79569ec57a.json';
        
        if (!file_exists($credentials_path)) {
            throw new Exception('Firebase Service Account credentials file not found');
        }

        $credentials = file_get_contents($credentials_path);
        $credentials_array = json_decode($credentials, true);

        // Generate JWT
        $now = time();
        $token = array(
            "iss" => $credentials_array['client_email'],
            "scope" => "https://www.googleapis.com/auth/firebase.messaging",
            "aud" => "https://oauth2.googleapis.com/token",
            "exp" => $now + 3600,
            "iat" => $now
        );

        $jwt = $this->generate_jwt($token, $credentials_array['private_key']);

        // Request access token
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, 'https://oauth2.googleapis.com/token');
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query([
            'grant_type' => 'urn:ietf:params:oauth:grant-type:jwt-bearer',
            'assertion' => $jwt
        ]));
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        
        $response = curl_exec($ch);
        curl_close($ch);
        
        $auth_data = json_decode($response, true);
        
        return $auth_data['access_token'];
    }

    /**
     * Fungsi private untuk generate JWT
     */
    private function generate_jwt($payload, $private_key) {
        $header = json_encode(['typ' => 'JWT', 'alg' => 'RS256']);
        
        $segments = array(
            base64_encode($header),
            base64_encode(json_encode($payload))
        );
        
        $signing_input = implode('.', $segments);
        
        $signature = '';
        openssl_sign($signing_input, $signature, $private_key, 'SHA256');
        
        $segments[] = base64_encode($signature);
        
        return implode('.', $segments);
    }

    /**
     * Fungsi private untuk melakukan request ke Firebase FCM v1
     */
    private function send($message) {
        try {
            $access_token = $this->get_access_token();
            
            $headers = array(
                'Authorization: Bearer ' . $access_token,
                'Content-Type: application/json'
            );

            $ch = curl_init();
            curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/v1/projects/' . $this->project_id . '/messages:send');
            curl_setopt($ch, CURLOPT_POST, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
            curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($message));
            
            $result = curl_exec($ch);
            curl_close($ch);
            
            return json_decode($result, true);
        } catch (Exception $e) {
            return array('error' => $e->getMessage());
        }
    }
}