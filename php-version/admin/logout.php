<?php
require_once __DIR__ . '/../lib/helpers.php';
$_SESSION = [];
session_destroy();
redirect(base_path() . '/admin/login.php');
