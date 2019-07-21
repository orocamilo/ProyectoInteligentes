CREATE OR REPLACE FUNCTION distancia_euclidiana(dat1 integer,dat2 integer)
RETURNS numeric AS
$BODY$
DECLARE
    flor1 "Iris"%rowtype;
    flor2 "Iris"%rowtype;
    distancia1 numeric (100,4);
    distancia2 numeric (100,4);
    distancia3 numeric (100,4);
    distancia4 numeric (100,4);
    distanciaE numeric (100,4);
BEGIN
    select * from "Iris"
        into flor1 
            where id=dat1;
    select * from "Iris"
        into flor2 
            where id=dat2;
distancia1:=power(flor1."anchoSepalo" - flor2."anchoSepalo",2);
distancia2:=power(flor1."altoSepalo" - flor2."altoSepalo",2);
distancia3:=power(flor1."altoPetalo" - flor2."altoPetalo",2);
distancia4:=power(flor1."anchoPetalo" - flor2."anchoPetalo",2);
distanciaE:=sqrt(distancia1+distancia2+distancia3+distancia4);
RETURN distanciaE;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

--select distancia_euclidiana(1,id), id from "Iris";
create or replace function mas_cercano(id integer, centroides integer[])
returns integer
as
$BODY$
declare
    distancia integer := 2147483647;
    menor integer := -1;
    aux integer;
begin
    for i in 1..array_length(centroides,1) loop
        aux := distancia_euclidiana(id,centroides[i]);
        if distancia > aux then
            distancia := aux;
            menor := centroides[i];
        end if;
    end loop;
    return menor;
end; 
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

create or replace function actualizarCentroides(INOUT centroides integer array )
returns integer array as
$BODY$
DECLARE
    promedio real:=0;
    cent integer := 0;
    aux integer;
BEGIN
    for i in 1..array_length(centroides,1) 
    loop 
        select avg(sqrt(power("anchoPetalo",2) + power("altoPetalo",2)+ power("altoSepalo",2)+ power("anchoSepalo",2)))
        into promedio
        from "Iris"
        where centroides[i] = centroide;
        aux := centroides[i];
        select id
        into cent
        from "Iris"
        where centroide = aux and abs(sqrt(power("anchoPetalo",2) + power("altoPetalo",2)+ power("altoSepalo",2)+ power("anchoSepalo",2))-promedio) = (select min(abs(sqrt(power(flor."anchoPetalo",2) + power(flor."altoPetalo",2)+ power(flor."altoSepalo",2)+ power(flor."anchoSepalo",2))-promedio)) 
                                                                                                                                                             from "Iris" flor);
        centroides[i]:= cent;                                                                                                                                                        
    end loop;
    return;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

create or replace function cambiaron(centroides integer array)
returns boolean as 
$BODY$
DECLARE
    bandera boolean:=False;
    i "Iris"%rowtype;
BEGIN
    for i in select distinct centroide from "Iris"
    loop
        for j in 1..array_length(centroides,1)
        loop
            if not i.centroide = centroides[j] then
                bandera:= True;
            end if;
            exit when bandera;
        end loop;
    end loop;

    return bandera;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;


create or replace function kmeans(k integer)
returns integer as 
$BODY$
DECLARE
    centroides integer array;
    menor integer;
    mayor integer;
    index integer:=1;
BEGIN
    select max(id), min(id)
    into mayor, menor
    from "Iris";
    for i in 1..k
    loop
        centroides[index] := trunc(random()*mayor+menor);
        index:=index + 1;
    end loop;
    loop
        update "Iris"
        set "centroide" = mas_cercano(id,centroides);
        centroides := actualizarCentroides(centroides);
        exit when not cambiaron(centroides);
    end loop;
RETURN 0;
END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;

select kmeans(3);
select * from "Iris" order by centroide;
select distinct centroide from "Iris";