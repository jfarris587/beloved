<?php   
$dbhost = 'db.soic.indiana.edu';
$dbuser = 'i494f17_team29';
$dbpass = 'my+sql=i494f17_team29';
$dbdata = 'i494f17_team29';
$conn = mysql_connect($dbhost, $dbuser, $dbpass);



if(! $conn ) {
   die('Could not connect: ' . mysql_error());
}

$client = $_POST['client'];
$date = $_POST['pickupdate'];
$time = $_POST['pickuptime'];
$fromnumber = $_POST['pickupnumber'];
$fromstreet = $_POST['pickupstreet'];
$fromcity = $_POST['pickupcity'];
$fromstate = $_POST['pickupstate'];
$fromzip = $_POST['pickupzip'];
$tonumber = $_POST['destnumber'];
$tostreet = $_POST['deststreet'];
$tocity = $_POST['destcity'];
$tostate = $_POST['deststate'];
$tozip = $_POST['destzip'];
$reason = $_POST['reason'];
$request = $_POST['request'];


//INSERT DATA INTO DRIVER DATABASE
$sql = "INSERT INTO request (client, date, time, pickupNumber, pickupStreet, pickupCity, pickupState, pickupZip, destinationNumber, destinationStreet, destinationCity, destinationState, destinationZip, reason, requests)
VALUES ($client, '$date', '$time', '$fromnumber', '$fromstreet', '$fromcity', '$fromstate', '$fromzip', '$tonumber', '$tostreet', '$tocity', '$tostate', '$tozip', '$reason', '$request')";

mysql_select_db('i494f17_team29');

$retval = mysql_query($sql, $conn);

if(! $retval ) {
   die('Could not get data: ' . mysql_error());
}
else{
   echo("New Request Added to Databse");
}


?>

