<?php
error_reporting(0); //no need for this in prod servers
/*
*   AUTH
*/
$request_api_key = getallheaders()['apikey'];
if (file_get_contents(__DIR__ . '/api.key') != $request_api_key){
    $ret = array();
    $ret['status'] = "ERROR";
    $ret['message'] = "INVALID_KEY";
    http_response_code(403);
    echo json_encode($ret);
    die();

}
/*
*   SIMPLE DIY ROUTER MANAGER
*/
$request_route = preg_replace('{/$}', '', $_SERVER["REQUEST_URI"]);
$request_method = $_SERVER['REQUEST_METHOD'];
if ($request_route == "/fluid/covid" && $request_method == "POST") {
    require_once __DIR__ . "/routes/covid.route.php";
}
else{
    $ret = array();
    $ret['status'] = "ERROR";
    $ret['message'] = "BAD_ROUTE";
    http_response_code(400);
    echo json_encode($ret);
    die();
}