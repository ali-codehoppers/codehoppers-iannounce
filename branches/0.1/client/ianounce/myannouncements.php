<?php
	if($_POST['pageNum']==1){
		echo "<Myannouncements>
	<announcement>
		<id>1</id>		
		<Description>This is description of an announcement which is announced near you :D</Description>
		<timestamp>Date and time </timestamp>
		<averageRating> 4.5</averageRating>		
		<longitude>100.5</longitude>
		<latitude>95.52</latitude>
		<noOfComments>35</noOfComments>
	</announcement>
	</Myannouncements>";
	
	}
	else{
		echo "<MyAnnouncements>
	<announcement>
		<id>35</id>		
		<Description>:|</Description>
		<timestamp>Date and time </timestamp>
		<averageRating> 4.5</averageRating>		
		<longitude>100.5</longitude>
		<latitude>95.52</latitude>
		<noOfComments>35</noOfComments>
	</announcement>	
	<announcement>
		<id>2</id>		
		<Description>waka waka</Description>
		<timestamp>11:50 02/06/11 </timestamp>
		<averageRating> 2.5</averageRating>		
		<longitude>111.1</longitude>
		<latitude>33.35</latitude>
		<noOfComments>15</noOfComments>
	</announcement>	
	<announcement>
		<id>3</id>		
		<Description>boomz</Description>
		<timestamp>11:50 02/06/11 </timestamp>
		<averageRating> 2.5</averageRating>		
		<longitude>31.13</longitude>
		<latitude>516.156</latitude>
		<noOfComments>25</noOfComments>
	</announcement>	
</MyAnnouncements>		
		";	
	}
?>