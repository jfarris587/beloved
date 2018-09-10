<!DOCTYPE html>
<html lang="en">
<head>
  <title>Delete Pickup</title>
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

    var submitReady = false;

    function prepareButton()
    { 
       document.getElementById('submitbtn').onclick = function()
       {

        if (document.getElementById("choose_schedule").selected==true) {
          submitReady = false;
          document.getElementById('driver').style.color = "red";
        }  

        submitReady = true;    
        if(submitReady){
          document.getElementById('registrationForm').submit();
        }
      }
    }

  </script>
  <style type="text/css">
    .submit:hover{
      background: red;
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

        <h3 class="header"><span>Deleting Pickup</span></h3>

        <form id="registrationForm" method="post" action="lib/delete.php">
          <div class="form-row">

            <div class="col-md-12 col-sm-12">
              <label>Scheduled Pickup</label>
              <select class="form-control" name="scheduled" id="scheduled">
                <option value="Choose Scheduled" id="choose_schedule" selected>Choose Pickup</option>
                <?php
                $dbhost = 'db.soic.indiana.edu';
                $dbuser = 'i494f17_team29';
                $dbpass = 'my+sql=i494f17_team29';
                $dbdata = 'i494f17_team29';
                $conn = mysql_connect($dbhost, $dbuser, $dbpass);

                if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
              }


              $sql = "SELECT c.id, CONCAT(c.fname, ' ', c.lname) as client, CONCAT(d.fname, ' ', d.lname) as driver, LOWER(TIME_FORMAT(r.time, '%l:%i %p')) as time, DATE_FORMAT(r.date, '%M %e, %Y') as date
                FROM request r
                INNER JOIN client c  ON r.client = c.id 
                INNER JOIN pickup p ON r.id = p.request
                INNER JOIN driver d ON d.id = p.driver";
        
              mysql_select_db('i494f17_team29');
              $result = mysql_query($sql, $conn);

              if (mysql_num_rows($result) > 0) {
                while ($row = mysql_fetch_assoc($result)) {
                    $client = $row['client'];
                    $driver = $row['driver'];

                    $date = $row['date'];
                    $time = $row['time'];

                    $id = $row['id'];

                    echo "<option value='$id'>CLIENT: $client      DRIVER: $driver      DATE: $date      TIME: $time     </option>";
                }
              }
              ?>

        </select>
            </div>
          </div>

          <div class="form-row">
           <div class="col-md-3 col-sm-3">
             <button class="btn btn-primary submit" id="submitbtn" style="" type="button">Delete</button>
           </div>
         </div>
       </form>
      </div>


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