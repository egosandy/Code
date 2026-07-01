<?php

class Voucher_model extends CI_model
{
    public function getAllcm()
    {
        $this->db->where('point.id != 0');
        return  $this->db->get('point')->result_array();
    }

    public function getallvoucher()
    {
        $this->db->where('home = 4');
        return  $this->db->get('fitur')->result_array();
    }

    public function tambahcm($data)
    {
        $this->db->insert('point', $data);
    }

    public function hapuscm($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('point');
    }

    public function ubahcm($data, $id)
    {
        $this->db->set('nama', $data['nama']);
        $this->db->set('point', $data['point']);
        $this->db->set('reward', $data['reward']);
        $this->db->set('tipe', $data['tipe']);
        $this->db->set('status', $data['status']);
        $this->db->where('id', $id);
        $this->db->update('point');
    }
}
