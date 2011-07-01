<?php
	if($_POST['pageNum']==1){
		echo "
			<announcements>
	<announcement>
		<id>1</id>
		<announcer>Awais01</announcer>
		<Description>This is description of an announcement which is announced near you :D</Description>
		<timestamp>Date and time </timestamp>
		<averageRating> 4.5</averageRating>
		<currentUserRating>1</currentUserRating>	
		<longitude>100.5</longitude>
		<latitude>95.52</latitude>
		<noOfComments>35</noOfComments>
	</announcement>
	</announcements>		
		";
	
	}
	else{
		echo "
		<announcements>
	<announcement>
		<id>35</id>
		<announcer>AwaisAkhtar</announcer>
		<Description>this is long description can be of limited words though :D :D :D xD ;) ;]</Description>
		<timestamp>Date and time </timestamp>
		<averageRating> 4.5</averageRating>
		<currentUserRating>0</currentUserRating>	
		<longitude>100.5</longitude>
		<latitude>95.52</latitude>
		<noOfComments>35</noOfComments>
	</announcement>
	
	<announcement>
		<id>2</id>
		<announcer>Umer</announcer>
		<Description>waka waka</Description>
		<timestamp>11:50 02/06/11 </timestamp>
		<averageRating> 2.5</averageRating>
		<currentUserRating>-1</currentUserRating>		
		<longitude>111.1</longitude>
		<latitude>33.35</latitude>
		<noOfComments>15</noOfComments>
	</announcement>
	
	<announcement>
		<id>3</id>
		<announcer>Awais</announcer>
		<Description>boomz</Description>
		<timestamp>11:50 02/06/11 </timestamp>
		<averageRating> 2.5</averageRating>
		<currentUserRating>1</currentUserRating>		
		<longitude>31.13</longitude>
		<latitude>516.156</latitude>
		<noOfComments>25</noOfComments>
	</announcement>
	
</announcements>		
		";	
	}
?>