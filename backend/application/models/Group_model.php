<?php


class Group_model extends CI_model
{
    function __construct()
    {
        parent::__construct();
    }

    function get_all_group()
    {
        return $this->db->get('admin_level')->result_array();
    }

    function get_grooup_by_id($id)
    {
    	return $this->db->get_where('admin_level', ['id' => $id])->row_array();
    }

    function insert_data_group($data)
    {
        return $this->db->insert('admin_level', $data);
    }

    function update_data_group($data)
    {
    	$this->db->set('nama', $data['nama']);
    	$this->db->set('keterangan', $data['keterangan']);
    	$this->db->where('id', $data['id']);
        return $this->db->update('admin_level', $data);

    }
    
    /*menus*/
     function get_menu_use1r($level)
    {
        $this->db->select('menu_kode, GetMenuWebLabel(menu_kode) as menu_label,
          GetMenuWebUrl(menu_kode) as menu_url,
          GetMenuWebIcon(menu_kode) as menu_icon,
          GetMenuGroup(menu_kode) as menu_group');
        $this->db->where('menu_group', $level);
        $this->db->order_by('menu_kode', 'ASC');
        return $this->db->get('kode_menu_web')->result();
    }
    
    function get_menu_user($level)
    {
        $this->db->select('kode_menu_web.menu_kode as menu_kode,
        data_menu_web.menu_label as menu_label,
        data_menu_web.menu_url as menu_url,
        data_menu_web.menu_icon as menu_icon,
        data_menu_web.menu_group as menu_group');
        $this->db->join('data_menu_web', 'kode_menu_web.menu_kode = data_menu_web.menu_kode', 'LEFT');
         $this->db->where('kode_menu_web.menu_group', $level);
        $this->db->order_by('kode_menu_web.menu_kode', 'ASC');
        return $this->db->get('kode_menu_web')->result();
        
    }

    function get_all_menu()
    {
        $this->db->order_by('menu_kode', 'ASC');
        return $this->db->get('data_menu_web')->result();
    }
}
