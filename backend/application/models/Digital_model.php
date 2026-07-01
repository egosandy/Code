<?php

class Digital_model extends CI_model
{
    function __construct()
    {
        parent::__construct();
        $this->load->model("Payment_model");
        $this->load->model("Pelanggan_model");
    }
    
    function get_setting_digi()
    {
        $this->db->where('id', 1);
        return $this->db->get('digi_setting')->row();
    }
    
    function update_data_setting($data){
        $this->db->where('id', 1);
        return $this->db->update('digi_setting', $data);
    }
    
    function get_data_kategori()
    {
        return $this->db->get('digi_kategori');
    }
    
    function get_data_operator($cond)
    {
        $this->db->select('digi_operator.*, digi_kategori.nama as nama_kategori');
        $this->db->join('digi_kategori', 'digi_operator.kategori = digi_kategori.id', 'LEFT');
        $this->db->where($cond);
        return $this->db->get('digi_operator');
    }
    
    function get_data_produk($cond)
    {
        $this->db->select('digi_produk.*, digi_operator.nama as nama_operator, 
        digi_operator.kategori as id_kategori, 
        digi_kategori.nama as nama_kategori,
        digi_kategori.tipe as tipe_kategori');
        $this->db->join('digi_operator', 'digi_produk.operator = digi_operator.id', 'LEFT');
        $this->db->join('digi_kategori', 'digi_operator.kategori = digi_kategori.id', 'LEFT');
        $this->db->where($cond);
        return $this->db->get('digi_produk');
    }
    
    function insert_data_transaksi($data)
    {
        return $this->db->insert('digi_transaction', $data);
    }
    
    function get_data_transaksi($cond)
    {
        $this->db->select('digi_transaction.*,
        digi_produk.nama as nama_produk,
        digi_operator.nama as brand,
        digi_kategori.nama as kategori,
        digi_kategori.tipe as tipe,
        digi_kategori.icon as icon');
        $this->db->join('digi_produk', 'digi_transaction.kode_produk = digi_produk.kode', 'LEFT');
        $this->db->join('digi_operator', 'digi_produk.operator = digi_operator.id', 'LEFT');
        $this->db->join('digi_kategori', 'digi_operator.kategori = digi_kategori.id', 'LEFT');
        $this->db->where($cond);
        $this->db->order_by('digi_transaction.id', 'DESC');
        return $this->db->get('digi_transaction')->result();
    }
    
    function get_data_transcation($cond)
    {
        $this->db->where($cond);
        return $this->db->get('digi_transaction')->row();
    }
    
    function update_data_transaction($data)
    {
        $this->db->where('invoice', $data['invoice']);
        return $this->db->update('digi_transaction', $data);
    }
    
    function request_digi_topup($param)
    {
        $data = new stdClass();
        $cond = array('id_user' => $param->id);
        $saldo_user = $this->Payment_model->get_saldo($cond);
        
        $conds = array(
            'id'=> $param->id
        );
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        
        $cond_produk = array(
            'digi_produk.kode' => $param->kode_produk
        );
        
        $produk = $this->get_data_produk($cond_produk)->row();
        
        if(empty($produk)){
            $data->error = 'Produk tidak ditemukan';
            return $data;
        }
        
        $harga = $produk->harga;
        $admin = $produk->admin;
        $total = $harga + $admin;
        
        if($saldo_user->saldo < $total){
            $data->error = 'Saldo anda tidak mencukupi untuk melakukan transaksi.';
            return $data;
        }
        
        
        $no_trx = 'POB'.date('yHmids');
        $setting = $this->get_setting_digi();
        
        if($setting->is_demo > 0){
            $key = $setting->key_development;
            $testing = true;
        }else{
            $key = $setting->key_production;
             $testing = false;
        }
        
        $payloads = array(
            'username' => $setting->username,
            'buyer_sku_code' => $param->kode_produk,
            'customer_no' => $param->nomor_tagihan,
            'ref_id'    => $no_trx,
            'sign' => MD5($setting->username.$key.$no_trx),
            'testing' => $testing
        );
        
        $send_request = $this->request_topup($payloads);
        if($send_request['http_code'] == 200){
             $resapi = json_decode($send_request['body']);
             if($resapi->data->rc == '00' || $resapi->data->rc == '03'){
                 
                $data_trx = [
                     'invoice' => $no_trx,
                     'id_user' => $param->id,
                     'kode_produk' => $param->kode_produk,
                     'harga' => $harga,
                     'admin' => $admin,
                     'total' => $total,
                     'nomor_tagihan' => $param->nomor_tagihan,
                     'trx_rc' => $resapi->data->rc,
                     'trx_message' => $resapi->data->message,
                     'trx_status' => $resapi->data->status,
                     'trx_sn' => $resapi->data->sn,
                     'status' => 0,
                     'regtime' => date('Y-m-d H:i:s')
                ];
                
                $save_transaction = $this->insert_data_transaksi($data_trx);
                if($this->db->affected_rows() < 1){
                    $data->error = "Failed db transaction (10)";
                    return $data;
                }
                
                $param_trx = array('invoice'=> $no_trx);
                $data = $this->get_data_transcation($param_trx);
                if($resapi->data->rc == '00'){
                    // update status success
                    $data_update = [
                        'invoice' => $no_trx,
                        'status' => 1
                    ];
                    
                    $update_trans = $this->update_data_transaction($data_update);
                    // cut saldo user
                    if($setting->is_demo == '0'){
                        $data_ins = array(
                            'id_user' => $param->id,
                            'jumlah' => $total,
                            'bank' => 'Digi',
                            'nama_pemilik' => $nama_pengirim,
                            'rekening' => 'wallet',
                            'type' => 'PPOB-'
                        );
                        $ins_trans = $this->db->insert('wallet', $data_ins);
                        if ($ins_trans) {
                            $this->db->where('id_user', $param->id);
                            $upd = $this->db->update('saldo', array('saldo' => ($saldo_user->saldo - $total)));
                        } else {
                            $data->error = 'Failed db transaction (11)';
                            return $data;
                        }
                    }
                    
                }
                
                // set data for result
                return $data;
                 
             }else{
                $data->error = json_decode($send_request['body'])->data->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }
             
        }else{
            $data->error = json_decode($send_request['body'])->data->message;
            $data->data = json_decode($send_request['body']);
            return $data;
        }
        
        return $data;
    }
    
    function check_status_request($param){
        $data = new stdClass();
        $cond = array('id_user' => $param->id);
        $saldo_user = $this->Payment_model->get_saldo($cond);
        
        $conds = array(
            'id'=> $param->id
        );
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        
        $cond_produk = array(
            'digi_produk.kode' => $param->kode_produk
        );
        
        $produk = $this->get_data_produk($cond_produk)->row();
        
        if(empty($produk)){
            $data->error = 'Produk tidak ditemukan';
            return $data;
        }
        
        $harga = $produk->harga;
        $admin = $produk->admin;
        $total = $harga + $admin;
        
        $no_trx = $param->ref_id;
        $setting = $this->get_setting_digi();
        
        if($setting->is_demo > 0){
            $key = $setting->key_development;
        }else{
            $key = $setting->key_production;
        }
        
        $payloads = array(
            'username' => $setting->username,
            'buyer_sku_code' => $param->kode_produk,
            'customer_no' => $param->nomor_tagihan,
            'ref_id'    => $no_trx,
            'sign' => MD5($setting->username.$key.$no_trx)
        );
        
        $send_request = $this->request_topup($payloads);
        if($send_request['http_code'] == 200){
             $resapi = json_decode($send_request['body']);
             
             if($resapi->data->rc == '00'){
                $param_trx = array('invoice'=> $no_trx);
                $data = $this->get_data_transcation($param_trx);
                if($data->status == '0'){
                    if($resapi->data->rc == '00'){
                    // update status success
                    $data_update = [
                        'invoice' => $no_trx,
                        'status' => 1,
                        'trx_rc' => $resapi->data->rc,
                        'trx_message' => $resapi->data->message,
                        'trx_status' => $resapi->data->status,
                        'trx_sn' => $resapi->data->sn
                    ];
                    
                    $update_trans = $this->update_data_transaction($data_update);
                    
                    // cut saldo user
                    if($setting->is_demo == '0'){
                        $data_ins = array(
                            'id_user' => $param->id,
                            'jumlah' => $total,
                            'bank' => 'Digi',
                            'nama_pemilik' => $nama_pengirim,
                            'rekening' => 'wallet',
                            'type' => 'PPOB-'
                        );
                        $ins_trans = $this->db->insert('wallet', $data_ins);
                        if ($ins_trans) {
                            $this->db->where('id_user', $param->id);
                            $upd = $this->db->update('saldo', array('saldo' => ($saldo_user->saldo - $total)));
                        } else {
                            $data->error = 'Failed db transaction (11)';
                            return $data;
                        }
                    }
                    
                }
                
                // set data for result
                return $data;
                }
                
             }else if($resapi->data->rc == '03'){
                $data->error = json_decode($send_request['body'])->data->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }else{
                $data_update = [
                    'invoice' => $no_trx,
                    'status' => 2,
                    'trx_rc' => $resapi->data->rc,
                    'trx_message' => $resapi->data->message,
                    'trx_status' => $resapi->data->status
                ];
                    
                $update_trans = $this->update_data_transaction($data_update); 
                 
                $data->error = json_decode($send_request['body'])->data->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }
             
        }else{
            $data->error = json_decode($send_request['body'])->data->message;
            $data->data = json_decode($send_request['body']);
            return $data;
        }
        
        return $data;
        
    }
    
    function check_status_pasca_request($param){
        $data = new stdClass();
        $cond = array('id_user' => $param->id);
        $saldo_user = $this->Payment_model->get_saldo($cond);
        
        $conds = array(
            'id'=> $param->id
        );
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        
        $no_trx = $param->ref_id;
        $setting = $this->get_setting_digi();
        
        if($setting->is_demo > 0){
            $key = $setting->key_development;
            $testing = true;
        }else{
            $key = $setting->key_production;
            $testing = false;
        }
        
        $payloads = array(
            'commands' => 'status-pasca',
            'username' => $setting->username,
            'buyer_sku_code' => $param->kode_produk,
            'customer_no' => $param->nomor_tagihan,
            'ref_id'    => $no_trx,
            'sign' => MD5($setting->username.$key.$no_trx),
            'testing' => $testing,
        );
        $param_trx = array('invoice'=> $no_trx);
        $data = $this->get_data_transcation($param_trx);
        $send_request = $this->request_topup($payloads);
        if($send_request['http_code'] == 200){
             $resapi = json_decode($send_request['body']);
             
             if($resapi->data->rc == '00'){
               
                
                if($data->status == '0'){
                    if($resapi->data->rc == '00'){
                    // update status success
                    $harga = $resapi->data->selling_price;
                    $admin = $resapi->data->admin;
                    $total = $harga + $admin;
                    $data_update = [
                        'invoice' => $no_trx,
                        'harga' => $harga,
                        'admin' => $admin,
                        'total' => $total,
                        'nama_pelanggan' => is_null($resapi->data->customer_name) ? '' : $resapi->data->customer_name,
                         'detail' => is_null(json_encode($resapi->data->desc)) ? '' : json_encode($resapi->data->desc),
                        'status' => 1,
                        'trx_rc' => $resapi->data->rc,
                        'trx_message' => $resapi->data->message,
                        'trx_status' => $resapi->data->status,
                        'trx_sn' => $resapi->data->sn
                    ];
                    
                    $update_trans = $this->update_data_transaction($data_update);
                    
                    // cut saldo user
                    if($setting->is_demo == '0'){
                        $data_ins = array(
                            'id_user' => $param->id,
                            'jumlah' => $total,
                            'bank' => 'Digi',
                            'nama_pemilik' => $nama_pengirim,
                            'rekening' => 'wallet',
                            'type' => 'PPOB-'
                        );
                        $ins_trans = $this->db->insert('wallet', $data_ins);
                        if ($ins_trans) {
                            $this->db->where('id_user', $param->id);
                            $upd = $this->db->update('saldo', array('saldo' => ($saldo_user->saldo - $total)));
                        } else {
                            $data->error = 'Failed db transaction (11)';
                            return $data;
                        }
                    }
                    
                }
                
                // set data for result
                return $data;
                }
                
             }else if($resapi->data->rc == '03'){
                return $data;
             }else{
                $data_update = [
                    'invoice' => $no_trx,
                    'status' => 2,
                    'trx_rc' => $resapi->data->rc,
                    'trx_message' => $resapi->data->message,
                    'trx_status' => $resapi->data->status
                ];
                    
                $update_trans = $this->update_data_transaction($data_update); 
                 
                $data->error = json_decode($send_request['body'])->data->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }
             
        }else{
            $data->error = json_decode($send_request['body'])->data->message;
            $data->data = json_decode($send_request['body']);
            return $data;
        }
        
        return $data;
        
    }
    
    function inquiry_pln($param)
    {
        $data = new stdClass();
        $payloads = array(
            'commands' => 'pln-subscribe',
            'customer_no' => $param->nomor_tagihan,
        );
        
        $send_request = $this->request_topup($payloads);
        if($send_request['http_code'] == 200){
            $resapi = json_decode($send_request['body']);
            return $resapi->data;
                 
        }else{
            $data->error = json_decode($send_request['body'])->data->message;
            $data->data = json_decode($send_request['body']);
            return $data;
        }
        
        return $data;
    }
    
    function inquiry_pasca($param)
    {
        $data = new stdClass();
        $no_trx = 'POB'.date('yHmids');
        $setting = $this->get_setting_digi();
        
        if($setting->is_demo > 0){
            $key = $setting->key_development;
            $testing = true;
        }else{
            $key = $setting->key_production;
            $testing = false;
        }
        $payloads = array(
            'commands' => 'inq-pasca',
            'username' => $setting->username,
            'buyer_sku_code' => $param->kode_produk,
            'customer_no' => $param->nomor_tagihan,
            'ref_id'    => $no_trx,
            'sign' => MD5($setting->username.$key.$no_trx),
            'testing'=> $testing,
        );
        
        $send_request = $this->request_pasca($payloads);
        if($send_request['http_code'] == 200){
             $resapi = json_decode($send_request['body']);
             $resultdata = $resapi->data;
             if($resultdata->rc == '00'){
                $result = array(
                     'invoice' => $resultdata->ref_id,
                     'nomor_tagihan' => $resultdata->customer_no,
                     'nama_pelanggan' => $resultdata->customer_name,
                     'kode_produk' => $resultdata->buyer_sku_code,
                     'admin' => $resultdata->admin,
                     'nominal' => $resultdata->selling_price,
                    
                );
                 
                return $result;
             }else{
                $data->error = $resultdata->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }
             
        }else{
            $resapi = json_decode($send_request['body']);
            $resultdata = $resapi->data;
            $data->error = $resultdata->message;
            $data = $resultdata;
           
        }
        return $data;
    }
    
    function payment_pasca($param)
    {
        $data = new stdClass();
        $cond = array('id_user' => $param->id);
        $saldo_user = $this->Payment_model->get_saldo($cond);
        
        $conds = array(
            'id'=> $param->id
        );
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        
         
        
        $cond_produk = array(
            'digi_produk.kode' => $param->kode_produk
        );
        
        $produk = $this->get_data_produk($cond_produk)->row();
        
        if(empty($produk)){
            $data->error = 'Produk tidak ditemukan';
            return $data;
        }
        
        $setting = $this->get_setting_digi();
        
       
        if($setting->is_demo > 0){
            $key = $setting->key_development;
            $testing = true;
        }else{
            $key = $setting->key_production;
            $testing = false;
        }
        
        $no_trx = $param->invoice;
        
        $payloads = array(
            'commands' => 'pay-pasca',
            'username' => $setting->username,
            'buyer_sku_code' => $param->kode_produk,
            'customer_no' => $param->nomor_tagihan,
            'ref_id'    => $param->invoice,
            'sign' => MD5($setting->username.$key.$no_trx),
            'testing' => $testing
        );
        
        //  echo json_encode($payloads);die();
        
        $send_request = $this->request_pasca($payloads);
        if($send_request['http_code'] == 200){
             $resapi = json_decode($send_request['body']);
             if($resapi->data->rc == '00' || $resapi->data->rc == '03'){
                 
                
                $data_trx = [
                     'invoice' => $no_trx,
                     'id_user' => $param->id,
                     'kode_produk' => $param->kode_produk,
                     'nomor_tagihan' => $param->nomor_tagihan,
                     'trx_rc' => $resapi->data->rc,
                     'trx_message' => $resapi->data->message,
                     'trx_status' => $resapi->data->status,
                     'trx_sn' => is_null($resapi->data->sn) ? '' : $resapi->data->sn,
                     'status' => 0,
                     'regtime' => date('Y-m-d H:i:s')
                ];
                
                $save_transaction = $this->insert_data_transaksi($data_trx);
                if($this->db->affected_rows() < 1){
                    $data->error = "Failed db transaction (10)";
                    return $data;
                }
                
                $param_trx = array('invoice'=> $no_trx);
                $data = $this->get_data_transcation($param_trx);
                if($resapi->data->rc == '00'){
                    // update status success
                    $harga = $resapi->data->selling_price;
                    $admin = $resapi->data->admin;
                    $total = $harga + $admin;
                    
                    $data_update = [
                        'invoice' => $no_trx,
                        'harga' => $harga,
                        'admin' => $admin,
                        'total' => $total,
                        'nama_pelanggan' => is_null($resapi->data->customer_name) ? '' : $resapi->data->customer_name,
                         'detail' => is_null(json_encode($resapi->data->desc)) ? '' : json_encode($resapi->data->desc),
                        'status' => 1
                    ];
                    
                    $update_trans = $this->update_data_transaction($data_update);
                    // cut saldo user
                    if($setting->is_demo == '0'){
                        
                        $data_ins = array(
                            'id_user' => $param->id,
                            'jumlah' => $total,
                            'bank' => 'Digi',
                            'nama_pemilik' => $nama_pengirim,
                            'rekening' => 'wallet',
                            'type' => 'PPOB-'
                        );
                        $ins_trans = $this->db->insert('wallet', $data_ins);
                        if ($ins_trans) {
                            $this->db->where('id_user', $param->id);
                            $upd = $this->db->update('saldo', array('saldo' => ($saldo_user->saldo - $total)));
                        } else {
                            $data->error = 'Failed db transaction (11)';
                            return $data;
                        }
                    }
                    
                }
                
                // set data for result
                return $data;
                 
             }else{
                $data->error = json_decode($send_request['body'])->data->message;
                $data->data = json_decode($send_request['body']);
                return $data;
             }
        }else{
            $resapi = json_decode($send_request['body']);
            $resultdata = $resapi->data;
            $data->error = $resultdata->message;
            $data = $resultdata;
           
        }
        return $data;
    }
    
    function request_topup($data)
    {
       
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
        curl_setopt($ch, CURLOPT_URL, "https://api.digiflazz.com/v1/transaction");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 60);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        
         $result = array(
            'body' => curl_exec($ch),
            'http_code' => curl_getinfo($ch, CURLINFO_HTTP_CODE),
		  );
        curl_close($ch);
        
        $insert_data = array(
            'tipe' => 1,
            'request' => json_encode($data),
            'response' => json_encode($result),
            'regtime' => date('Y-m-d H:i:s')
        );

        $this->db->insert('log_api', $insert_data);
        
        return $result;
    }
    
    function request_pasca($data)
    {
       
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
        curl_setopt($ch, CURLOPT_URL, "https://api.digiflazz.com/v1/transaction");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 60);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        
         $result = array(
            'body' => curl_exec($ch),
            'http_code' => curl_getinfo($ch, CURLINFO_HTTP_CODE),
		  );
        curl_close($ch);
        
        $insert_data = array(
            'tipe' => 2,
            'request' => json_encode($data),
            'response' => json_encode($result),
            'regtime' => date('Y-m-d H:i:s')
        );

        $this->db->insert('log_api', $insert_data);
        
        return $result;
    }
    
    function cek_status_topup($param){
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));
        curl_setopt($ch, CURLOPT_URL, "https://api.digiflazz.com/v1/transaction");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 60);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        
         $result = array(
            'body' => curl_exec($ch),
            'http_code' => curl_getinfo($ch, CURLINFO_HTTP_CODE),
		  );
        curl_close($ch);
        
        $insert_data = array(
            'tipe' => 1,
            'request' => json_encode($data),
            'response' => json_encode($result),
            'regtime' => date('Y-m-d H:i:s')
        );

        $this->db->insert('log_api', $insert_data);
        
        return $result;
    }
    
    function update_transaksi_callback($param){
        // get ref id from vendor
        $no_trx = $param->data->ref_id;
        // get data transaction
        $param_trx = array('invoice'=> $no_trx);
        $data_trans = $this->get_data_transcation($param_trx);
        
        // get saldo user
        $id_user = $data_trans->id_user;
        $cond = array('id_user' => $id_user);
        $saldo_user = $this->Payment_model->get_saldo($cond);
        // get data user
        $conds = array(
            'id'=> $id_user
        );
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        $token = $sender->token;
        
        // get setting digiflazz
        $setting = $this->get_setting_digi();
        
        if($setting->is_demo > 0){
            $key = $setting->key_development;
            $testing = true;
        }else{
            $key = $setting->key_production;
            $testing = false;
        }
                
        // get data produk
        $cond_produk = array(
            'digi_produk.kode' => $param->data->buyer_sku_code
        );
                
        $produk = $this->get_data_produk($cond_produk)->row();
        
        if($produk->tipe_kategori > 1){
            $message = "Pembayaran ".$produk->nama_kategori. " ".$produk->nama;
            $harga = $param->data->selling_price;
            $admin = $param->data->admin;
            $total = $harga + $admin;
            
            $customer_name = $param->data->customer_name;
            $detail = json_encode($param->data->desc);
            
        }else{
            $message = "Pembelian ".$produk->nama_kategori. " ".$produk->nama;
            $harga = $produk->harga;
            $admin = $produk->admin;
            $total = $harga + $admin;
            $customer_name = "";
            $detail = "";
        }
        
        if($param->data->rc == '00'){
            $title = "Berhasil";
            $data_update = [
                    'invoice' => $no_trx,
                    'harga' => $harga,
                    'admin' => $admin,
                    'total' => $total,
                    'nama_pelanggan' => $customer_name,
                    'detail' => $detail,
                    'status' => 1,
                    'trx_rc' => $param->data->rc,
                    'trx_message' => $param->data->message,
                    'trx_status' => $param->data->status,
                    'trx_sn' => is_null($param->data->sn) ? "" : $param->data->sn
            ];
                    
            $update_trans = $this->update_data_transaction($data_update);
                
            if($update_trans){
                if($setting->is_demo == '0'){
                    $data_ins = array(
                        'id_user' => $id_user,
                        'jumlah' => $total,
                        'bank' => 'Digi',
                        'nama_pemilik' => $nama_pengirim,
                        'rekening' => 'wallet',
                        'type' => 'PPOB-'
                    );
                    $ins_trans = $this->db->insert('wallet', $data_ins);
                    if ($ins_trans) {
                        $this->db->where('id_user', $id_user);
                        $upd = $this->db->update('saldo', array('saldo' => ($saldo_user->saldo - $total)));
                    } else {
                        $data->error = 'Failed db transaction (11)';
                        return $data;
                    }
                }
            }
        }else{
            $title = "Gagal";
            $data_update = [
                'invoice' => $no_trx,
                'status' => 2,
                'trx_rc' => $param->data->rc,
                'trx_message' => $param->data->message,
                'trx_status' => $param->data->status
            ];
                    
            $update_trans = $this->update_data_transaction($data_update);
        }
        
        $this->send_notif("Transaksi ".$title, $message." ".$title, $token);
        
    }
    
    public function send_notif($title, $message, $regid)
    {

        $data = array(
            'title' => $title,
            'message' => $message,
            'type' => 0
        );
        $senderdata = array(
            'data' => $data,
            'to' => $regid
        );

        $headers = array(
            'Content-Type: application/json',
            'Authorization: key=' . keyfcm
        );
        $curl = curl_init();

        curl_setopt_array($curl, array(
            CURLOPT_URL => "https://fcm.googleapis.com/fcm/send",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "POST",
            CURLOPT_POSTFIELDS => json_encode($senderdata),
            CURLOPT_HTTPHEADER => $headers,
        ));

        $response = curl_exec($curl);
        $err = curl_error($curl);
        // echo json_encode($response);die();
        curl_close($curl);
    }
}