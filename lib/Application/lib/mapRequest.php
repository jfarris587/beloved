<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$id = $_POST["id"];
$rank = $_POST["rank"];
$date = $_POST["date"];
$json_array = array();

if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

else{

  $sql = "SET @rownum=0";
  mysql_select_db('i494f17_team29');
  $retval = mysql_query($sql, $conn);

  $sql = "SELECT @rownum := @rownum + 1 AS rank, clientID, fname, lname, time, requests, reason, pickupNumber, pickupStreet, pickupCity, pickupState, pickupZip, destinationNumber, destinationStreet, 
destinationCity, destinationState, destinationZip, odtime

        FROM (
        SELECT c.id as clientID, c.fname, c.lname, TIME_FORMAT(r.time, '%l:%i %p') as time, r.requests, r.reason, r.pickupNumber, r.pickupStreet, r.pickupCity, r.pickupState, r.pickupZip, r.destinationNumber, r.destinationStreet, 
r.destinationCity, r.destinationState, r.destinationZip, r.time as odtime
  
  FROM request r  
        INNER JOIN client c  ON r.client = c.id 
        INNER JOIN pickup p ON r.id = p.request
        INNER JOIN driver d ON d.id = p.driver

  WHERE r.date = '$date'
  AND d.id = '$id'
  GROUP BY odtime

          ) AS tab
          GROUP BY rank
          HAVING rank = '$rank'";

   mysql_select_db('i494f17_team29');

   $retval = mysql_query($sql, $conn);

   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }

   if (mysql_num_rows($retval) > 0) {
     while ($row = mysql_fetch_assoc($retval)) {
         $json_array[] = $row;
         
      } 
      echo json_encode($json_array);
   }
   else{
      echo "no more results";
   }

}



mysql_close($conn);
?>