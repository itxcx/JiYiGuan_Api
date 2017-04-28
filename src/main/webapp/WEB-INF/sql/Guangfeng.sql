#namespace("guangfeng")

  #sql("count")
    SELECT COUNT(*) FROM table_guangfeng
    WHERE system_status = 1
    AND guangfeng_ip = #p(guangfeng_ip)
    AND (system_create_time BETWEEN #p(start_time) AND #p(end_time))
  #end

  #sql("list")
    SELECT
    guangfeng_id,
    guangfeng_name
    FROM table_guangfeng
    WHERE system_status = 1
    #if(guangfeng_name)
      #set(guangfeng_name = "%" + guangfeng_name + "%")
      AND guangfeng_name LIKE #p(guangfeng_name)
    #end
    ORDER BY system_create_time DESC
    #if(n > 0)
      LIMIT #p(m), #p(n)
    #end
  #end

  #sql("resultList")
    SELECT
        1 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 1
    UNION ALL
    SELECT
        2 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 2
    UNION ALL
    SELECT
        3 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 3
    UNION ALL
    SELECT
        4 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 4
    UNION ALL
    SELECT
        5 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 5
    UNION ALL
    SELECT
        6 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 6
    UNION ALL
    SELECT
        7 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 7
    UNION ALL
    SELECT
        8 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 8
    UNION ALL
    SELECT
        9 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 9
    UNION ALL
    SELECT
        10 AS guangfeng_number,
        COUNT(guangfeng_number) AS guangfeng_count
        FROM table_guangfeng
        WHERE system_status = 1
        AND guangfeng_number = 10
  #end

  #sql("find")
    SELECT
    *
    FROM table_guangfeng
    WHERE system_status = 1
    AND guangfeng_id = #p(guangfeng_id)
  #end

  #sql("delete")
    UPDATE table_guangfeng SET
    system_update_user_id = #p(system_update_user_id),
    system_update_time = #p(system_update_time),
    system_status = 0
    WHERE guangfeng_id = #p(guangfeng_id)
  #end

#end