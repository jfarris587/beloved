<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$id = $_POST["id"];
//$id = 104;
$json_array = array();

if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

else{

  $sql = "SELECT c.id, c.fname, c.lname, TIME_FORMAT(r.time, '%l:%i %p') as time, r.requests, r.reason, r.pickupNumber, r.pickupStreet, r.pickupCity, r.pickupState, r.pickupZip, r.destinationNumber, r.destinationStreet, r.destinationCity, r.destinationState, r.destinationZip
   FROM request as r, client as c
   WHERE r.id = '$id'
   AND r.client = c.id";

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