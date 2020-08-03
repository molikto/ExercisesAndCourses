select count(distinct person_id) from crew 
where (title_id in (
    select distinct(title_id) from crew inner join people on (crew.person_id = people.person_id)
     where people.name = 'Mark Hamill' and people.born = '1951'
)) and (category = 'actor' or category = 'actress');