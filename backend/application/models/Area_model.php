<?php

class Area_model extends CI_model
{
    public function getAllcm()
    {
        $this->db->where('area.id != 0');
        return  $this->db->get('area')->result_array();
    }

     public function getallvoucher()
    {
        $this->db->where('home = 4');
        return  $this->db->get('fitur')->result_array();
    }

    public function tambahcm($data)
    {
        $this->db->insert('area', $data);
    }

    public function hapuscm($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('area');
    }

     public function ubahcm($data, $id)
    {
        $this->db->set('kota', $data['kota']);
        $this->db->set('promo', $data['promo']);
        $this->db->set('rate1', $data['rate1']);
        $this->db->set('rate2', $data['rate2']);
        $this->db->set('rate3', $data['rate3']);
        $this->db->set('id', $data['id']);
        $this->db->set('status', $data['status']);
        $this->db->where('id', $id);
        $this->db->update('area');
    }
}
