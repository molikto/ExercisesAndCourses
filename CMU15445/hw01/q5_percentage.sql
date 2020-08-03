with total_table(total) as (
 select count(1) from titles
)
select (premiered / 10) || '0s' as ds, round(count(1) *100.0 / total, 4)  from titles, total_table where premiered is not null group by ds order by ds;

