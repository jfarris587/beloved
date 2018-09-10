<?php   
   $dbhost = 'db.soic.indiana.edu';
   $dbuser = 'i494f17_team29';
   $dbpass = 'my+sql=i494f17_team29';
   $dbdata = 'i494f17_team29';
   $conn = mysql_connect($dbhost, $dbuser, $dbpass);
 
   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }
     

$fname = $_POST['fname'];
$mname = $_POST['mname'];
$lname = $_POST['lname'];
$street = $_POST['street'];
$city = $_POST['city'];
$state = $_POST['state'];
$zip = $_POST['zip'];
$dob = $_POST['dob'];
$medicade = $_POST['medicade'];
$phone = $_POST['phone'];
$physical = $_POST['physical'];
$mental = $_POST['mental'];



//INSERT DATA INTO DRIVER DATABASE
$sql = "INSERT INTO client (fname, mname, lname, street, city, state, zip, dob, medicade, phone, physical, mental)
		VALUES ('$fname', '$mname', '$lname', '$street', '$city', '$state', '$zip', '$dob', 
		'$medicade', '$phone', '$physical', '$mental')";


mysql_select_db('i494f17_team29');

 $retval = mysql_query( $sql, $conn);
      
if(! $retval ) {
   die('Could not get data: ' . mysql_error());
}
else{
   echo "New Client Added to Database";
}

?>
