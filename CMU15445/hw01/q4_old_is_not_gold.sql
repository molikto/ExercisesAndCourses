select (premiered / 10) || '0s' as ds, count(1) from titles where premiered is not null group by ds order by ds;
