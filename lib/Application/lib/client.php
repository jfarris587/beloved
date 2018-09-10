<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$id = $_POST["id"];
$json_array = array();

if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

else{

   $sql = "SELECT CONCAT(c.fname, ' ', c.lname) AS name, c.street, CONCAT(c.city, ', ', c.state) AS state, c.physical, c.mental, DATE_FORMAT(c.dob, '%M %e, %Y') as dob, c.phone
         FROM client as c
         WHERE c.id = '$id'";
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