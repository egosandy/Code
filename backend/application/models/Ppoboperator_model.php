<?php

class Ppoboperator_model extends CI_model
{
    public function getAllservice()
    {
        $this->db->select('*');
        $this->db->order_by('nama', 'ASC');
        return  $this->db->get('ppob_fitur')->result_array();
    }
    public function getfiturbyid($id)
    {
        $this->db->select('*');
        $this->db->where('kode', $id);
        return $this->db->get('ppob_fitur')->row_array();
    }

 

    public function ubahdatafitur($data)
    {
        $this->db->set('ikon', $data['ikon']);
        $this->db->set('nama', $data['nama']);
        $this->db->set('tipe', $data['tipe']);
        $this->db->set('status', $data['status']);
        $this->db->where('kode', $data['kode']);
        $this->db->update('ppob_fitur');
    }

    public function tambahdatafitur($data)
    {
        $this->db->insert('ppob_fitur', $data);
    }

    public function hapusservicebyId($id)
    {
        $this->db->where('id_fitur', $id);
        $this->db->delete('fitur');

        $this->db->where('untuk_fitur', $id);
        $this->db->delete('voucher');
    }
}
