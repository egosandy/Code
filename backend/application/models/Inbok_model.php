<?php

class Inbok_model extends CI_Model 
{
  public function getdata()
    {
        return  $this->db->get('pelanggan')->result_array();
    }
    public function kirimdata($data)
    {
        return $this->db->insert('inbok', $data);
    }
} 
