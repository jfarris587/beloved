<!DOCTYPE html>
<html lang="en">
<head>
  <title>Schedule Pickup</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="font-awesome-4.6.3/css/font-awesome.min.css">
  <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:300,600|Raleway:100,300,400|Roboto:100" rel="stylesheet">
  <link rel="icon" type="image/png" href="img/icon.png" sizes="16x16">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">

  <script type="text/javascript">
    window.onload = prepareButton;

    var required = ["choose_driver",
                    "choose_request"];
    var submitReady = false;

    function prepareButton()
    { 
       document.getElementById('submitbtn').onclick = function()
       {

        submitReady = true;

        if (document.getElementById("choose_driver").selected==true) {
          submitReady = false;
          document.getElementById('driver').style.color = "red";
        }  
        if (document.getElementById("choose_request").selected==true) {
          submitReady = false;
          document.getElementById('request').style.color = "red";        
        }       

        if(submitReady){
          document.getElementById('registrationForm').submit();
        }
      }
    }

  </script>
  <style type="text/css">
    .resetButton{
    border: none;
    background-color: transparent;
    text-align: left;
    padding: 0;
    height: 25px; 

  }
  .resetButton:hover{
    background-color: transparent;
    border: none;
    opacity: 1;
  }

  .resetButton a{
    opacity: .5;
    color: white;
  }

  .resetButton a:hover{
    opacity: 1;
    color: white;
    text-decoration: none;
  }
  </style>
</head>
<body>


  <!-- NAVIGATION BAR -->
  <div id="myNavbar" class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </div>  

      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li><a href="home.html">Home</a></li>
          <li><a href="driver.php">Add Driver</a></li>
          <li><a href="client.php">Add Client</a></li>
          <li><a href="request.php">Request Pickup</a></li>
          <li><a href="schedule.php">Schedule Pickup</a></li>
        </ul>
      </div> 
    </div>
  </div>
  <!-- END NAVIGATION BAR -->

  <!-- MAIN FORM CONTAINER -->
  <div class="container overall">
    <div class="row">
      <div class="col-md-8 col-sm-8">

        <h3 class="header"><span>Scheduling Pickup</span></h3>

        <form id="registrationForm" method="post" action="lib/schedule.php">
          <div class="form-row">
            <div class="col-md-6 col-sm-6">
              <label>Driver</label>
              <select class="form-control" name="driver" id="driver">
                <option value="Choose Driver" id="choose_driver" selected>Choose Driver</option>
                <?php
                $dbhost = 'db.soic.indiana.edu';
                $dbuser = 'i494f17_team29';
                $dbpass = 'my+sql=i494f17_team29';
                $dbdata = 'i494f17_team29';
                $conn = mysql_connect($dbhost, $dbuser, $dbpass);

                if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
              }


              $sql = "SELECT * FROM driver ORDER BY lname ASC";
              mysql_select_db('i494f17_team29');
              $result = mysql_query($sql, $conn);

              if (mysql_num_rows($result) > 0) {
                while ($row = mysql_fetch_assoc($result)) {
                    $id = $row['id'];
                    $fname = $row['fname'];
                    $lname = $row['lname'];
      
                    echo "<option value='$id'>$fname $lname</option>";
                }
              }
              ?>

             </select>
            </div>

            <div class="col-md-6 col-sm-6">
              <label>Request</label>
              <select class="form-control" name="request" id="request">
                <option value="Choose Request" id="choose_request" selected>Choose Request</option>
                <?php
                $dbhost = 'db.soic.indiana.edu';
                $dbuser = 'i494f17_team29';
                $dbpass = 'my+sql=i494f17_team29';
                $dbdata = 'i494f17_team29';
                $conn = mysql_connect($dbhost, $dbuser, $dbpass);

                if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
              }


              $sql = "SELECT id, client, date, LOWER(TIME_FORMAT(time, '%l:%i%p')) as time FROM request WHERE date > CURDATE() ORDER BY time";
        
              mysql_select_db('i494f17_team29');
              $result = mysql_query($sql, $conn);

              if (mysql_num_rows($result) > 0) {
                while ($row = mysql_fetch_assoc($result)) {
                    $id = $row['client'];
                    $requestId = $row['id'];
  
                    $date = $row['date'];
                    $time = $row['time'];

                    $sql2 = "SELECT * FROM client WHERE id='$id'";
                    mysql_select_db('i494f17_team29');
                    $result2 = mysql_query($sql2, $conn);
                    $row2 = mysql_fetch_assoc($result2);
                    $fname = $row2['fname'];
                    $lname = $row2['lname'];

                    echo "<option value='$requestId'>$fname $lname : $date $time</option>";
                }
              }
              ?>

        </select>
            </div>
          </div>

          <div class="form-row">
           <div class="col-md-3 col-sm-3">
             <button class="btn btn-primary submit" id="submitbtn" type="button">Schedule</button>
           </div>           
         </div>
       </form>
       
      </div>
      <button class="btn btn-primary" type="submit"><a href="delete.php">Unschedule Pickup</a></button>


<!-- END MAIN FORM CONTAINER -->

<!--  LOGO -->
<div class="col-md-4 col-sm-4 image img-responsive">
  <img class="img-responsive" id="logo" src="img/whitelogo.png">
</div>
<!-- END LOGO -->
</div>
</div>
</div>




</body>
</html>