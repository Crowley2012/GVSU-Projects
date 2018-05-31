<?php

if(isset($_POST["next"])) {
	$month = $_GET['month'];
	$year = $_GET['year'];

	$date = time();

	if($month == null){
		$month = date('m', $date);
	}

	if($year == null){
		$year = date('Y', $date);
	}

	$month = $month + 1;

	if($month == 13){
		$month = 1;
		$year = $year + 1;
	}

	header("Location: http://www.cis.gvsu.edu/~crowleys/cal.php?month=$month&year=$year");
}

if(isset($_POST["previous"])) { 
	$month = $_GET['month'];
	$year = $_GET['year'];

	$date = time();

	if($month == null){
		$month = date('m', $date);
	}

	if($year == null){
		$year = date('Y', $date);
	}

	$month = $month - 1;

	if($month == 0){
		$month = 12;
		$year = $year - 1;
	}

	header("Location: http://www.cis.gvsu.edu/~crowleys/cal.php?month=$month&year=$year");
}

function setup(){

		echo $color;

		date_default_timezone_set('America/New_York');

		$date = time();

		$day = date('d', $date);
		$month = $_GET['month'];
		$year = $_GET['year'];

		if($month == null){
			$month = date('m', $date);
		}

		if($year == null){
			$year = date('Y', $date);
		}

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

		echo "<table border=30 width=800 height=400 align='center' class='cal'>";
		echo "<tr><th colspan=60 class='monthyear'> $title $year </th></tr>";
		echo "<tr class='days'>	<td width=75 ><center>Sun</center></td>
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
			echo "<td class='empty'></td>";
			$blank = $blank-1;
			$day_count++;
		}

		$day_num = 1;

		while($day_num <= $days_in_month){
			echo "<td class='dates'> $day_num </td>";
			$day_num++;
			$day_count++;

			if($day_count > 7){
				echo "</tr><tr>";
				$day_count = 1;
			}
		}

		while($day_count > 1 && $day_count <=7){
			echo "<td class='empty'></td>";
			$day_count++;
		}

		echo "</tr></table></center>";
}

?>
