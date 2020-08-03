with tr as (
    select primary_title, 
      sum(votes) as votes,
      sum(rating * votes) as rating
    from ratings inner join titles on titles.title_id = ratings.title_id
    where titles.type is 'movie'
    group by titles.title_id
)
, ars(c) as (
    select sum (rating) / sum(votes) from tr
)
select 
  primary_title,
  ((tr.votes*1.0/(tr.votes + 25000)) * (tr.rating / tr.votes) + (25000.0 / (tr.votes + 25000)) * c) as rating
from tr, ars
order by rating desc limit 250;
