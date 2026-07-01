<?php
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Payment extends REST_Controller{
    public function __construct()
    {
        parent::__construct();
        
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Pelanggan_model');
        $this->load->model('Payment_model');
        $this->load->model('Merchantapi_model');
        $this->load->model('Driver_model');
        $this->load->model('Donasi_model');
        $this->load->model('Notification_model');
        date_default_timezone_set('Asia/Jakarta');
    }
    
    function index_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $payment = $this->Payment_model->get_all_jenis_payment();
        
        
        $message = array(
            'code' => '200',
            'message' => 'success',
            'data' => $payment
        );
        $this->response($message, 200);
    }
    
    
    /*add api to transfer fitur and donate 040722*/
    
    function check_saldo_post(){
        // if (!isset($_SERVER['PHP_AUTH_USER'])) {
        //     header("WWW-Authenticate: Basic realm=\"Private Area\"");
        //     header("HTTP/1.0 401 Unauthorized");
        //     return false;
        // }
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $saldo = $this->Pelanggan_model->saldouser($dec_data->id);
        if($saldo->num_rows() > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'saldo' => $saldo->row('saldo'),
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '201',
                'message' => 'failed',
                'data' => []
            );
            $this->response($message, 201);
        }
        
        
        
    }
    
    
    function check_valid_trf_post(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $input = file_get_contents("php://input");
        $req = json_decode($input);
        
       
        $data = $this->Payment_model->get_valid_trf($req);
        
        $resp['message'] = 'success';
        $resp['data'] = $data;
        
        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
        
    }
    
    function transfer_saldo_post(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $input = file_get_contents("php://input");
        $req = json_decode($input);
        
        $data = $this->Payment_model->add_transfer($req);
        $resp['message'] = 'success';
        $resp['data'] = $data;

        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
        
    }
    
    function donasi_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $donasi = $this->Donasi_model->get_data_donasi_all();
        if(count($donasi) > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $donasi
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '201',
                'message' => 'failed get data',
            );
            $this->response($message, 200);
        }
        
    }
    
    function donasi_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
         $input = file_get_contents("php://input");
        $req = json_decode($input);
        
        $data = $this->Donasi_model->add_donasi($req);
        $resp['message'] = 'success';
        $resp['data'] = $data;

        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
    }
    
    function donatur_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $id = $this->input->get('id', TRUE);
        $donasi = $this->Donasi_model->get_data_donatur_api($id);
        if(count($donasi) > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $donasi
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '201',
                'message' => 'failed get data',
            );
            $this->response($message, 200);
        }
    }
    
    function donasiwd_get()
    {
         if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $id = $this->input->get('id', TRUE);
        $donasi = $this->Donasi_model->get_data_withdraw($id);
        if(count($donasi) > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $donasi
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '201',
                'message' => 'failed get data',
            );
            $this->response($message, 200);
        }
    }
    
    function transaksi_get(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $iduser = $this->input->get('id_user', true);
        
        $cond = array(
            'payment_transaksi.status > ' => 0,
            'payment_transaksi.id_user' => $iduser
        );
       
        $payment = $this->Payment_model->get_data_payment_by_cond($cond);
        
        
        $message = array(
            'code' => '200',
            'message' => 'success',
            'data' => $payment
        );
        $this->response($message, 200);
    }
    
    function ewallet_post(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $this->db->where('id', 1);
        $this->db->where('status', 1);
        $setting = $this->db->get('payment_setting')->row();
        if(count($setting) > 0){
            $key_production = $setting->key_production;
            $key_development = $setting->key_demo;
            $is_demo = $setting->is_demo;
            
            $input = file_get_contents("php://input");
            $req = json_decode($input);
            $id_method = $req->id_method;
            $method = $this->Payment_model->get_data_payment_by_id($id_method);
            
            if(count($method) > 0){
                if($method['jenis'] == '1'){
                    $payload = array(
                        'id_user' => $req->id_user,
                        'amount' => $req->amount,
                        'fee' => $method['biaya'],
                        'channel_id' => $id_method,
                        'channel_code' => $method['channel_code'],
                        'channel_name' => $method['nama'],
                        'phone' => $req->phone,
                        'key_production' => $key_production,
                        'key_development' => $key_development,
                        'is_demo' => $is_demo
                    );
                    $data = $this->Payment_model->charge_ewallet_xendit($payload);
                    if(!empty($data->error)){
                        $resp['code'] = 201;
                        $resp['message'] = $data->message;
                        $resp['error_code'] = $data->error;
                        $resp['error_message'] =  $data->message;
                    }else{
                        $res = $data->data;
                        $data_resp = array(
                            'trxid'  => $res['reference_id'],
                            'status' => $res['status'],
                            'nominal' => $res['charge_amount'],
                            'method' => $res['channel_code'],
                            'is_redirect' => $res['is_redirect_required'],
                            'redirect_url'  => $res['actions'],
                              
                        );
                        
                        $resp['code'] = 200;
                        $resp['message'] = 'success';
                        $resp['data'] = $data_resp;
                    }
            
                    $this->response($resp, 200);
                }else{
                    $message = array(
                        'code' => 201,
                        'status' => '11',
                        'message' => 'Error selected channel',
                    );
                    $this->response($message, 201);
                }
            }else{
                $message = array(
                    'code' => 201,
                    'status' => '10',
                    'message' => 'Metode pembayaran tidak ditemukan/tidak aktif',
                );
                $this->response($message, 201);
            }
        }else{
            $message = array(
                'code' => 201,
                'status' => '10',
                'message' => 'Payment channel not available, please wait until active.',
            );
            $this->response($message, 201);
        }
    }
    
    function callback_ewallet_post(){
        $input = file_get_contents("php://input");
        $req = json_decode($input);

        log_message('error','callback_wallet: ' . json_encode($req));
        
        
        $update_trans = $this->Payment_model->update_trans_ewallet_callback($req);

        // $data = $this->Payment_model->success_top_ewallet_xendit($req);
        // $resp['message'] = 'success';
        // $resp['data'] = $data;

        // if(!empty($data->error)){
        //     $resp['message'] = $data->error;
        //     $resp['data'] = null;
        // }

        // echo $input;
        exit;
    }
    
    function check_ewallet_get(){
         if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $invoice = $this->input->get('invoice', true);
        // echo $invoice;die();
        $payment = $this->Payment_model->get_data_payment_by_invoice($invoice);
       
        $message = array(
            'code' => '200',
            'message' => 'success',
            'data' => $payment
        );
        $this->response($message, 200);
    }
    
    function va_post(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $this->db->where('id', 1);
        $this->db->where('status', 1);
        $setting = $this->db->get('payment_setting')->row();
        if(count($setting) > 0){
            $key_production = $setting->key_production;
            $key_development = $setting->key_demo;
            $is_demo = $setting->is_demo;
            
            $input = file_get_contents("php://input");
            $req = json_decode($input);
            $id_method = $req->id_method;
            $method = $this->Payment_model->get_data_payment_by_id($id_method);
            
            if(count($method) > 0){
                if($method['jenis'] == '2'){
                    $payload = array(
                        'id_user' => $req->id_user,
                        'name' => 'TRV'.ucwords($req->nama_depan).' '.ucwords($req->nama_belakang),
                        'amount' => $req->amount,
                        'fee' => $method['biaya'],
                        'channel_id' => $id_method,
                        'channel_code' => $method['channel_code'],
                        'channel_name' => $method['nama'],
                        'phone' => $req->phone,
                        'key_production' => $key_production,
                        'key_development' => $key_development,
                        'is_demo' => $is_demo
                    );
                    $data = $this->Payment_model->pending_top_va_xendit($payload);
                    $resp['code'] = '200';
                    $resp['message'] = 'success';
                    $resp['data'] = $data;
            
                    if(!empty($data->error)){
                        $err_message = json_decode($data->error, true);
                        $resp['code'] = '201';
                        $resp['message'] = $err_message['message'];
                        $resp['data'] = null;
                    }
            
                    $this->response($resp, 200);
                }else{
                    $message = array(
                        'code' => 201,
                        'status' => '11',
                        'message' => 'Error selected channel',
                    );
                    $this->response($message, 201);
                }
            }else{
                $message = array(
                    'code' => 201,
                    'status' => '10',
                    'message' => 'Metode pembayaran tidak ditemukan/tidak aktif',
                );
                $this->response($message, 201);
            }
        }else{
            $message = array(
                'code' => 201,
                'status' => '10',
                'message' => 'Payment channel not available, please wait until active.',
            );
            $this->response($message, 201);
        }
    }
    
    function callback_va_created_post(){
        $input = file_get_contents("php://input");
        log_message('debug',$input);

        $req = json_decode($input);
   
        $data = $this->Payment_model->update_status_va($req);
        $resp['message'] = 'success';
        $resp['data'] = $data;

        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }

        echo json_encode($data);
        exit;
    }
    
    function callback_fixed_va_xendit_post(){
        $input = file_get_contents("php://input");
        log_message('debug',$input);

        $req = json_decode($input);
   
        $data = $this->Payment_model->success_top_xendit($req);
        $resp['message'] = 'success';
        $resp['data'] = $data;

        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }

        echo json_encode($data);
        exit;
    }
    
    function retail_post(){
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        $this->db->where('id', 1);
        $this->db->where('status', 1);
        $setting = $this->db->get('payment_setting')->row();
        if(count($setting) > 0){
            $key_production = $setting->key_production;
            $key_development = $setting->key_demo;
            $is_demo = $setting->is_demo;
            
            $input = file_get_contents("php://input");
            $req = json_decode($input);
            
            $id_method = $req->id_method;
            $method = $this->Payment_model->get_data_payment_by_id($id_method);
            if(count($method) > 0){
                if($method['jenis'] == '3'){
                    $payload = array(
                        'id_user' => $req->id_user,
                        'name' => 'TRV'.ucwords($req->nama_depan).' '.ucwords($req->nama_belakang),
                        'amount' => $req->amount,
                        'fee' => $method['biaya'],
                        'channel_id' => $id_method,
                        'channel_code' => $method['channel_code'],
                        'channel_name' => $method['nama'],
                        'key_production' => $key_production,
                        'key_development' => $key_development,
                        'is_demo' => $is_demo
                    );
                    
                    $data = $this->Payment_model->deposit_retail_xendit($payload);
    
                    $resp['message'] = 'success';
                    $resp['data'] = $data;
            
                    if(!empty($data->error)){
                        $err_message = json_decode($data->error, true); 
                        $resp['message'] = $err_message['message'];
                        $resp['data'] = null;
                    }
            
                    $this->response($resp, 200);
                }else{
                    $message = array(
                        'code' => 201,
                        'status' => '11',
                        'message' => 'Error selected channel',
                    );
                    $this->response($message, 201);
                }
                
            }else{
                $message = array(
                    'code' => 201,
                    'status' => '10',
                    'message' => 'Metode pembayaran tidak ditemukan/tidak aktif',
                );
                $this->response($message, 201);
            }
    
            
        }else{
            $message = array(
                'code' => 201,
                'status' => '10',
                'message' => 'Payment channel not available, please wait until active.',
            );
            $this->response($message, 201);
        }
        

    }
    
    function callback_fixed_paid_post(){
        $input = file_get_contents("php://input");
        log_message('debug',$input);

        $req = json_decode($input);
   
        $data = $this->Payment_model->retail_paid_xendit($req);
        $resp['message'] = 'success';
        $resp['data'] = $data;

        if(!empty($data->error)){
            $resp['message'] = $data->error;
            $resp['data'] = null;
        }

        echo json_encode($data);
        exit;
    }
    
     
}