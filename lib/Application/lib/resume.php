<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);

$date = $_POST["date"];
$id = $_POST["id"];

if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

else{

   $sql = "SELECT r.id
	FROM request as r, client as c, driver as d, pickup as p
	WHERE date = '$date'
	AND r.client = c.id
	AND d.id = p.driver
	AND d.id = '$id'
	GROUP BY time
	LIMIT 1;";
   mysql_select_db('i494f17_team29');

   $retval = mysql_query($sql, $conn);

   if(! $retval ) {
      die('Could not get data: ' . mysql_error());
   }

   if (mysql_num_rows($retval) > 0) {
     while ($row = mysql_fetch_assoc($retval)) {
     	echo $row["id"];
      } 
   }
}

mysql_close($conn);
?>