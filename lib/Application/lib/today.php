<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$date = $_POST["today"];
$id = $_POST["id"];
$json_array = array();

if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

else{
   $sql = "SET @rownum=0";
   mysql_select_db('i494f17_team29');
   $retval = mysql_query($sql, $conn);

   $sql = "SELECT @rownum := @rownum + 1 AS rank, id, client, date, time, pickupCity, pickupState, odtime
FROM(

SELECT r.id, CONCAT(c.fname, ' ', c.lname) as client, r.date, LOWER(TIME_FORMAT(r.time, '%l:%i%p')) as time, r.pickupCity, r.pickupState, r.time as odtime
FROM request r  
        INNER JOIN client c  ON r.client = c.id 
        INNER JOIN pickup p ON r.id = p.request
        INNER JOIN driver d ON d.id = p.driver
WHERE date = '$date'
AND d.id = '$id'
GROUP BY odtime
) as tab";

/*
   $sql = "SELECT r.id, CONCAT(c.fname, ' ', c.lname) as client, r.date, LOWER(TIME_FORMAT(r.time, '%l:%i%p')) as time, r.pickupCity, r.pickupState
FROM request as r, client as c, driver as d, pickup as p
WHERE date = '$date'
AND r.client = c.id
AND d.id = p.driver
AND d.id = '$id'
GROUP BY time";
*/
   mysql_select_db('i494f17_team29');
   $retval = mysql_query($sql, $conn);

   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }

   if (mysql_num_rows($retval) > 0) {
     while ($row = mysql_fetch_assoc($retval)) {
         $json_array[] = $row;
      } 
   }
}

echo json_encode($json_array);

mysql_close($conn);
?>