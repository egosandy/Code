<?php
defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Placeholder model. File asli berisi "return true;" di dalam body class
 * (syntax error, fatal di PHP 7 & 8). Tidak direferensikan controller/model
 * manapun. Dinetralkan menjadi kelas kosong yang valid agar tidak memfatalkan
 * autoload/lint. Perilaku aplikasi tidak berubah.
 */
class Ci_ext_model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }
}
