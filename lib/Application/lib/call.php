<?php   
   $dbhost = 'db.soic.indiana.edu';
   $dbuser = 'i494f17_team29';
   $dbpass = 'my+sql=i494f17_team29';
   $dbdata = 'i494f17_team29';
   $conn = mysql_connect($dbhost, $dbuser, $dbpass);

   $fname = $_POST["id"];
   $lname = $_POST[""];


   if(! $conn ) {
      die('Could not connect: ' . mysql_error());
   }


$sql = "SELECT fname, lname FROM driver WHERE id='$id'";
   mysql_select_db('i494f17_team29');
   $retval = mysql_query( $sql, $conn);

   if(mysql_num_rows($retval) != 0){
      //Driver Exists
      

      $row = mysql_fetch_row($retval);
      $fname = $row[0];
      $lname = $row[1];



      $to = "jordfarr@indiana.edu";
      $subject = "BELOVED TRANSPORTATION: Call Driver";
      $headers = "From: driver@belovedtransportation.com";
      $body = "Dwayne, \n\n
         " . $fname . " " . $lname . " is requeting you to call him/her.";

      if(!mail($to,$subject,$body,$headers)){
       echo "Email failed to send. Please try again later...";
      }
      else{
         echo "Email Sent.";
      }
   }

   else{
      echo "Driver Doesn't Exist";
   }
   
   mysql_close($conn);

   ?>