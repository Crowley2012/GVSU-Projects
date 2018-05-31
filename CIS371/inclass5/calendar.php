<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html;charset=iso-8859-1"/>
	<link rel="stylesheet" href="style.css" type="text/css"/>
	<title>CS 371 Assignment 3</title>
</head>
<body>
	<center>
		<h1>Sean Crowley</h1>
		<h2>In-class 5</h2><br><br><br>
	</center>

	<?php 
		date_default_timezone_set('America/New_York');

		$date = time();

		$day = date('d', $date);
		$month = $_GET['month'];
		$year = date('Y', $date);

		$first_day = mktime(0, 0, 0, $month, 1, $year);
		
		$title = date('F', $first_day);

		$day_of_week = date('D', $first_day);

		switch($day_of_week){
			case "Sun": $blank = 0; break;
			case "Mon": $blank = 1; break;
			case "Tue": $blank = 2; break;
			case "Wed": $blank = 3; break;
			case "Thu": $blank = 4; break;
			case "Fri": $blank = 5; break;
			case "Sat": $blank = 6; break;
		}

		$days_in_month = cal_days_in_month(0, $month, $year);

		echo "<center><table border=30 width=800 height=400>";
		echo "<tr><th colspan=60> $title $year </th></tr>";
		echo "<tr>	<td width=75 ><center>Sun</center></td>
					<td width=75><center>Mon</center></td>
					<td width=75><center>Tues</center></td>
					<td width=75><center>Wed</center></td>
					<td width=75><center>Thurs</center></td>
					<td width=75><center>Fri</center></td>
					<td width=75><center>Sat</center></td>
			 </tr>";

		$day_count = 1;

		echo "<tr>";

		while($blank > 0){
			echo "<td></td>";
			$blank = $blank-1;
			$day_count++;
		}

		$day_num = 1;

		while($day_num <= $days_in_month){
			echo "<td> $day_num </td>";
			$day_num++;
			$day_count++;

			if($day_count > 7){
				echo "</tr><tr>";
				$day_count = 1;
			}
		}

		while($day_count > 1 && $day_count <=7){
			echo "<td></td>";
			$day_count++;
		}

		echo "</tr></table></center>";
	?>

</body>
</html>
