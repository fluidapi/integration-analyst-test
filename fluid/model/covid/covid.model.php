<?php
//best practice is to extend a class
//like class Covid extends FluidAPI {}
function covidModel($request_file){
    $csv_array = array_map("str_getcsv", $request_file);
    array_shift($csv_array);

    $parsed_array = array();
    foreach ($csv_array as $data) {
        $php_date = strtotime($data[0]);
        $month = date('m', $php_date);
        $year = date('Y', $php_date);
        $key = $year . '-' . $month;
        $parsed_array[$key]['cases'] += intval($data[1]);
        $parsed_array[$key]['deaths'] += intval($data[2]);
    }

    $return_array = array();
    foreach ($parsed_array as $month => $cases_and_deaths_arr) {
        $row = array();
        $row['month'] = $month;
        $return_array[] = array_merge($row, $cases_and_deaths_arr);
    }
    
    return $return_array;
}