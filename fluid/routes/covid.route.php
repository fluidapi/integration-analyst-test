<?php
require_once dirname(__DIR__) . "/controller/covid.controller.php";
if (!isset($_FILES['file']['error']) || is_array($_FILES['file']['error'])) {
    $ret = array();
    $ret['status'] = "ERROR";
    $ret['message'] = "FILE_ERROR";
    echo json_encode($ret);
    die();
}
$request_file_array = file($_FILES['file']['tmp_name']);
$response = covidController($request_file_array);
echo json_encode($response, JSON_PRETTY_PRINT); //no one-line clever code here