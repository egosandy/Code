<?php

class Profile_model extends CI_model
{
    public function getadmin($id)
    {
        $this->db->where('id', $id);
        return  $this->db->get('admin')->row_array();
    }

    public function ubahdataadmin($data)
    {
        $this->db->set('user_name', $data['user_name']);
        $this->db->set('email', $data['email']);
        $this->db->set('image', $data['image']);
        $this->db->set('password', $data['password']);

        $this->db->where('id', '1');
        $this->db->update('admin', $data);
    }
    
    /*model for admin*/
    function get_level()
    {
        return $this->db->get('admin_level')->result_array();
    }
    
    function get_admin_by_id($id)
    {
        $this->db->where('id', $id);
        return  $this->db->get('admin')->row_array();
    }
    
    function check_user_exist($user)
    {
        $this->db->where('user_name', $user);
        $res = $this->db->get('admin');
        if($res->num_rows() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    function get_admin_all()
    {
        $this->db->select('admin.id as id, admin.user_name as user_name, admin.nama as nama,
        admin.email as email, admin.image as image, admin.level as level, admin_level.nama as role, admin.status as status');
        $this->db->join('admin_level', 'admin.level = admin_level.id', 'left');
        return $this->db->get('admin')->result_array();
    }
    
    function add_data_admin($data)
    {
        return $this->db->insert('admin', $data);
    }
    
    public function update_data_admin($data)
    {
        $this->db->set('user_name', $data['user_name']);
        $this->db->set('email', $data['email']);
        $this->db->set('image', $data['image']);
        $this->db->set('password', $data['password']);

        $this->db->where('id', $data['id']);
        $this->db->update('admin', $data);
    }
    
    function delete_data_admin($id)
    {
        $this->db->where('id', $id);
        return $this->db->delete('admin');
    }
}
