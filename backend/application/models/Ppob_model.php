<?php

class ppob_model extends CI_model
{
    public function getAllOperator()
    {
        return  $this->db->get('fitur_ppob')->result_array();
    }

    public function getpromocodebyid($id)
    {
        return $this->db->get_where('kodepromo', ['id_promo' => $id])->row_array();
    }

    public function hapuspromocodebyId($id)
    {
        $this->db->where('id_promo', $id);
        $this->db->delete('kodepromo');
    }

    public function addpromocode($data)
    {
        return $this->db->insert('kodepromo', $data);
    }

    public function cekpromo($code)
    {
        $this->db->select('*');
        $this->db->from('kodepromo');
        $this->db->where('kode_promo',$code);
        return $this->db->get(); 
    }

    public function getpromobyid($id)
    {
        $this->db->select('*');
        $this->db->from('kodepromo');
        $this->db->where('id_promo',$id);
        return $this->db->get(); 
    }

    public function editpromocode($data)
    {
        $this->db->where('id_promo', $data['id_promo']);
        return $this->db->update('kodepromo', $data);
    }
    
    public function get_data_transaksi()
    {
        $this->db->select('digi_transaction.*,
        digi_produk.nama as produk_nama');
        $this->db->join('digi_produk', 'digi_transaction.kode_produk = digi_produk.kode', 'LEFT');
        $this->db->order_by('digi_transaction.id', 'DESC');
        return $this->db->get('digi_transaction')->result_array();
    }
    
    /*new api digiflazz by autocrack 2022*/
    function get_setting_digi()
    {
        $this->db->where('id', 1);
        return $this->db->get('digi_setting')->row();
    }
    
    function get_all_kategori_produk()
    {
        $this->db->where('status', 1);
        $this->db->order_by('id', 'ASC');
        return $this->db->get('digi_kategori')->result_array();
    }
    
    public function get_kategori_by_id($id)
    {
        $this->db->where('id',$id);
        return $this->db->get('digi_kategori')->row_array(); 
    }
    
    function insert_kategori($data)
    {
        return $this->db->insert('digi_kategori', $data);
    }
    
    function update_kategori($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('digi_kategori', $data);
    }
    
    function delete_kategori($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('digi_kategori');
    }
    
    function get_all_operator()
    {
        $this->db->select('digi_operator.*, digi_kategori.nama as nama_kategori');
        $this->db->join('digi_kategori', 'digi_operator.kategori = digi_kategori.id', 'LEFT');
        $this->db->where('digi_operator.status', 1);
        return $this->db->get('digi_operator')->result_array();
    }
    
    function insert_operator($data)
    {
        // echo json_encode($data);die();
        return $this->db->insert('digi_operator', $data);
    }
    
     function update_operator($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('digi_operator', $data);
    }
    
    function delete_operator($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('digi_operator');
    }
    
    
    function get_all_produk()
    {
        $this->db->select('digi_produk.*, digi_operator.nama as nama_operator');
        $this->db->join('digi_operator', 'digi_produk.operator = digi_operator.id', 'LEFT');
        $this->db->where('digi_produk.status', 1);
        return $this->db->get('digi_produk')->result_array();
    }
    
    function insert_produk($data)
    {
        // echo json_encode($data);die();
        return $this->db->insert('digi_produk', $data);
    }
    
     function update_produk($data)
    {
        $this->db->where('id', $data['id']);
        return $this->db->update('digi_produk', $data);
    }
    
    function delete_produk($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('digi_produk');
    }
    

}