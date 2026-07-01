<?php

class Poin_model extends CI_model
{
    public function getAllPoin()
    {
        return  $this->db->get('poin')->result_array();
    }

    public function getpoinbyid($id)
    {
        return $this->db->get_where('poin', ['kode' => $id])->row_array();
    }

    public function hapuspoin($id)
    {
        $this->db->where('kode', $id);
        $this->db->delete('poin');
    }

    public function addpoin($data)
    {
        return $this->db->insert('poin', $data);
    }

    public function cekpoin($code)
    {
        $this->db->select('*');
        $this->db->from('poin');
        $this->db->where('kode',$code);
        return $this->db->get(); 
    }

    public function editpoin($data)
    {
        $this->db->where('kode', $data['kode']);
        return $this->db->update('poin', $data);
    }
     public function getdatabyid($id)
    {
        $this->db->select('*');
        $this->db->from('poin');
        $this->db->where('kode',$id);
        return $this->db->get(); 
    }

}