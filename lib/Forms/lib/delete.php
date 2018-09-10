<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);



if(! $conn ) {
	die('Could not connect: ' . mysql_error());
}

$client = $_POST['scheduled'];

//INSERT DATA INTO DRIVER DATABASE
$sql = "SELECT r.id
FROM request r
INNER JOIN client c  ON r.client = c.id 
INNER JOIN pickup p ON r.id = p.request

WHERE client = '$client'";

mysql_select_db('i494f17_team29');

$retval = mysql_query($sql, $conn);

if(mysql_num_rows($retval) != 0){
	$row = mysql_fetch_assoc($retval);
	$request = $row['id'];   


	$sql = "DELETE FROM pickup
	WHERE request = '$request'";

	mysql_select_db('i494f17_team29');
	$retval = mysql_query($sql, $conn);



	echo "Request unscheduled from driver";

}

else{
	echo $client;
}


?>

