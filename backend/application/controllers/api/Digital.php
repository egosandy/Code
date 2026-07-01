<?php
//'tes' => number_format(200 / 100, 0, ",", "."),
defined('BASEPATH') or exit('No direct script access allowed');
require APPPATH . '/libraries/REST_Controller.php';
class Digital extends REST_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->helper("url");
        $this->load->database();
        $this->load->model('Digital_model');
        date_default_timezone_set('Asia/Jakarta');
    }

    function index_get()
    {
        $this->response("Api for ppob kujek!", 200);
    }
    
    function kategori_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $resdata = $this->Digital_model->get_data_kategori();
        if($resdata->num_rows() > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $resdata->result()
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'error data',
                'data' => []
            );
            $this->response($message, 201);
        }
    }
    
    function operator_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $kategori = $this->input->get('id', TRUE);
        $cond = [
            'digi_operator.kategori' => $kategori,
            'digi_operator.status' => 1
        ];
        
        $resdata = $this->Digital_model->get_data_operator($cond);
        if($resdata->num_rows() > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $resdata->result()
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'Operator not found',
                'data' => []
            );
            $this->response($message, 201);
        }
        
    }
    
    function produk_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $id = $this->input->get('id', TRUE);
        $cond = [
            'digi_produk.operator' => $id,
            'digi_produk.status' => 1
        ];
        
        $resdata = $this->Digital_model->get_data_produk($cond);
        if($resdata->num_rows() > 0){
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $resdata->result()
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'Product not found',
                'data' => []
            );
            $this->response($message, 201);
        }
        
    }
    
    function pasca_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $kategori = $this->input->get('id', TRUE);
        $cond = [
            'digi_operator.kategori' => $kategori,
            'digi_operator.status' => 1
        ];
        
        $resdata = $this->Digital_model->get_data_operator($cond);
        if($resdata->num_rows() > 0){
            
            $data_operator = $resdata->row();
            
            $id_operator = $data_operator->id;
            
            $condx = [
                'digi_produk.operator' => $id_operator,
                'digi_produk.status' => 1
            ];
            
            $resdatax = $this->Digital_model->get_data_produk($condx);
            
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $resdatax->row()
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'error data',
            );
            $this->response($message, 201);
        }
    }
    
    function riwayat_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $id = $this->input->get('id', TRUE);
        $param_trx = array('digi_transaction.id_user'=> $id);
        $data = $this->Digital_model->get_data_transaksi($param_trx);
        if($data){
             $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $data
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'error data',
                'data' => []
            );
            $this->response($message, 201);
        }
    }
    
    function detail_get()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $invoice = $this->input->get('invoice', TRUE);
        $param_trx = array('invoice'=> $invoice);
        $data = $this->Digital_model->get_data_transaksi($param_trx);
        if($data){
             $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => $data
            );
            $this->response($message, 200);
        }else{
             $message = array(
                'code' => '404',
                'message' => 'error data',
                'data' => []
            );
            $this->response($message, 201);
        }
        
    }
    
    function callback_digi_post()
    {
        $input = file_get_contents("php://input");
        $req = json_decode($input);
        log_message('debug','callback_digiflazz: ' . json_encode($req));
        
        $this->Digital_model->update_transaksi_callback($req);
        
        $insert = [
            'data' => json_encode($req),
            'regtime' => date('Y-m-d H:i:s')
        ];
        
        $this->db->insert('log_callback', $insert);
        
        exit;
    }
    
    function topup_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
        $result = $this->Digital_model->request_digi_topup($data_req);
        
        $resp['status'] = '00';
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($result->error)){
            $resp['status'] = '01';
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
        
    }
    
    function check_status_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
        $result = $this->Digital_model->check_status_request($data_req);
        
        $resp['status'] = '00';
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($result->error)){
            $resp['status'] = '01';
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
        
    }
    
    function inquiry_token_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
        $result = $this->Digital_model->inquiry_pln($data_req);
        
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($data->error)){
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
    }
    
    function inquiry_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
        $result = $this->Digital_model->inquiry_pasca($data_req);
        
        $resp['status'] = '00';
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($result->error)){
            $resp['status'] = '01';
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
    }
    
    function payment_post()
    {
         if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
       
        $result = $this->Digital_model->payment_pasca($data_req);
        $resp['status'] = '00';
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($result->error)){
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
    }
    
    function check_status_pasca_post()
    {
        if (!isset($_SERVER['PHP_AUTH_USER'])) {
            header("WWW-Authenticate: Basic realm=\"Private Area\"");
            header("HTTP/1.0 401 Unauthorized");
            return false;
        }
        
        $data = file_get_contents("php://input");
        $data_req = json_decode($data);
        $result = $this->Digital_model->check_status_pasca_request($data_req);
        
        $resp['status'] = '00';
        $resp['message'] = 'success';
        $resp['data'] = $result;
        
        if(!empty($result->error)){
            $resp['status'] = '01';
            $resp['message'] = $result->error;
            $resp['data'] = null;
        }
        $this->response($resp, 200);
        
    }
}