<?php

class Donasi_model extends CI_model
{
    function get_donasi_by_id($id)
    {
        return $this->db->get_where('donasi', ['id' => $id])->row_array();
    }
    
    function get_data_donasi_all()
    {
        $this->db->where('status', 1);
        $get_data = $this->db->get('donasi');
        if($get_data->num_rows() > 0){
           foreach($get_data->result() as $value){
               $result[] = [
                   'id' => $value->id,
                   'nama_lembaga' => $value->nama_lembaga,
                   'alamat' => $value->alamat,
                   'phone' => $value->phone,
                   'judul' => $value->judul,
                   'deskripsi' => $value->deskripsi,
                   'gambar' => $value->gambar,
                   'tanggal_awal' => $value->tanggal_awal,
                   'tanggal_akhir' => $value->tanggal_akhir,
                   'status' => $value->status,
                   'regtime' => $value->regtime,
                   'total' => $this->get_total_donasi($value->id),
                   'withdraw' => $this->get_total_withdraw($value->id)
                ];
           }
        }else{
            $result = [];
        }

        
        return $result;
    }
    
    function get_total_donasi($id)
    {
        $this->db->select('SUM(masuk - keluar) as total');
        $this->db->where('status', 1);
        $this->db->where('id_donasi', $id);
        $this->db->group_by('id_donasi');
        $total =  $this->db->get('donasi_transaksi')->row('total');
   
        return is_null($total) ? 0 : $total;
    }
    
    function get_total_withdraw($id)
    {
        $this->db->select('SUM(jumlah) as total');
        $this->db->where('status', 1);
        $this->db->where('id_donasi', $id);
        $this->db->group_by('id_donasi');
        $total =  $this->db->get('donasi_withdraw')->row('total');
   
        return is_null($total) ? 0 : $total;
    }
    
    function insert_data_donasi($data)
    {
        return $this->db->insert('donasi', $data);
    }
    
    function update_data_donasi($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('donasi', $data);
    }
    
    public function get_saldo($param){
        $this->db->select('*')
                ->from('saldo')
                ->where($param)
                ->limit(1);
        return $this->db->get()->row();
    }
    
    function add_donasi($param){
        $data = new stdClass();
        
        $conds = array('id' => $param->id_user);
        $sender = $this->Pelanggan_model->get_data_pelanggan($conds)->row();
        $nama_pengirim = $sender->fullnama;
        $tokens = $sender->token;
        $phones = $sender->no_telepon;
        
        $cond = array('id_user' => $param->id_user);
        $saldoSender = $this->get_saldo($cond);
        
        if($saldoSender->saldo < $param->nominal){
            $data->error = 'Saldo anda tidak mencukupi untuk melakukan transaksi.';
            return $data;
        }
        
         $no_trx = 'DNS-'.date('yHmids');
          $data_trx = array(
            'invoice' => $no_trx,
            'id_donasi' => $param->id_donasi,
            'id_user' => $param->id_user,
            'nama_pengirim' => $param->nama,
            'masuk' => $param->nominal,
            'keluar' => 0,
            'status' => 1,
            'regtime' => date('Y-m-d H:i:s')
        );

        $verify = $this->db->insert('donasi_transaksi', $data_trx);
        if($this->db->affected_rows() < 1){
                $data->error = "Failed db transaction (1). transaction failed";
                return $data;
        }
        
        $data_saldo_sender = array(
            'saldo' => intval($saldoSender->saldo) - intval($param->nominal),
            'update_at' => date('Y-m-d H:i:s')
        );
        
        $this->db->where('nomor', $saldoSender->nomor);
        $updateSaldoSender = $this->db->update('saldo', $data_saldo_sender);
        if($this->db->affected_rows() < 1){
            $data->error = "Failed db transaction (2). saldo no updated";
            return $data;
        }
        
        $data_donasi = $this->get_donasi_by_id($param->id_donasi);
        
        $this->db->set('status', '1');
        $this->db->set('type', 'donasi-');
        $this->db->set('rekening', 'wallet');
        $this->db->set('bank', 'Saldo');
        $this->db->set('nama_pemilik', $nama_pengirim);
        $this->db->set('jumlah', $param->nominal);
        $this->db->set('id_user', $param->id_user);
        $this->db->insert('wallet');
        
        $title = 'Topjek';
        $desc = 'Terima kasih Anda telah mengirim donasi untuk '.$data_donasi['nama_lembaga']. ' sebesar Rp'.$param->nominal;
        $this->Notification_model->send_notif_topup($title, $param->id_user, $desc, $no_trx, $tokens);
        
        $data = $data_donasi;
        
        return $data;
    }
    
    
    public function get_data_donatur($id){
        $this->db->where('id_donasi', $id);
        $this->db->order_by('id', 'DESC');
        return $this->db->get('donasi_transaksi')->result();
        
    }
    
     public function get_data_donatur_api($id){
        $this->db->where('id_donasi', $id);
        $this->db->order_by('id', 'DESC');
        $get_data =  $this->db->get('donasi_transaksi');
        
        if($get_data->num_rows() > 0){
            foreach($get_data->result() as $value){
                if($value->masuk > 0){
                    $result[] = [
                        'id' => $value->id,
                        'invoice' => $value->invoice,
                        'id_donasi' => $value->id_donasi,
                        'id_user' => $value->id_user,
                        'nama_pengirim' => $value->nama_pengirim,
                        'masuk' => $value->masuk,
                        'keluar' => $value->keluar,
                        'status' => $value->status,
                        'regtime' => $value->regtime
                        
                    ];
                }
            }
        }else{
            $result = [];
        }
        
        return $result;
    }
    
    function get_data_withdraw($id){
        $this->db->select('donasi_withdraw.*, 
        donasi.judul as judul');
        $this->db->join('donasi', 'donasi_withdraw.id_donasi = donasi.id', 'LEFT');
        $this->db->where('donasi_withdraw.id_donasi', $id);
        $this->db->order_by('donasi_withdraw.id', 'DESC');
        return $this->db->get('donasi_withdraw')->result();
    }
    
    function insert_data_withdraw($data)
    {
        $save = $this->db->insert('donasi_withdraw', $data);
        if($save){
            $arr = [
                'invoice' => $data['invoice'],
                'id_donasi' => $data['id_donasi'],
                'id_user' => 0,
                'nama_pengirim' => 'withdraw',
                'masuk' => 0,
                'keluar' => $data['jumlah'],
                'status' => 1,
                'regtime' => date('Y-m-d H:i:s')
            ];
            
            $this->db->insert('donasi_transaksi', $arr);
        }
        
        return $save;
    }
    
    function delete_data_donasi($id)
    {
        $upd = array(
            'status' => 9
        );
        
        $this->db->where('id', $id);
        return $this->db->update('donasi', $upd);
    }
    
}