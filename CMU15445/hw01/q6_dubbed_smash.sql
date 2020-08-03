 select primary_title, count(1) as c from titles inner join akas on akas.title_id = titles.title_id group by titles.title_id order by c desc limit 10;
