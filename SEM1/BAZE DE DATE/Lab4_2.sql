--cursor
declare @TestID int, @TableID int, @ViewID int
declare @ViewName nvarchar(50), @TableName nvarchar(50)
declare @NoOfRows int, @proc NVARCHAR(100)

declare CursorTeste cursor forward_only

for select TestID from Tests

open CursorTeste

fetch next from CursorTeste into @TestID
while @@FETCH_STATUS=0 begin

	exec ('insert into TestRuns(Description,StartAt,EndAt) values ('+ @TestID +',convert(varchar(50),GETDATE()),null)')
	exec ('UPDATE TestRuns SET StartAt = GETDATE() WHERE Description =' + @TestID)
	
	--cursor delete
	declare CursorDelete cursor forward_only

	for select Name
	from Tables T inner join TestTables TT on T.TableID=TT.TableID
	where TT.TestID=@TestID
	order by Position

	open CursorDelete

	fetch next from CursorDelete into @TableName

	while @@FETCH_STATUS=0 begin
		exec ('DELETE FROM ' + @TableName)
		fetch next from CursorDelete into @TableName
	end

	close CursorDelete
	deallocate CursorDelete--

	--cursor insert
	declare CursorInsert cursor forward_only

	for select Name, NoOfRows, TT.TableID
	from Tables T inner join TestTables TT on T.TableID=TT.TableID
	where TT.TestID=@TestID
	order by Position desc

	open CursorInsert

	fetch next from CursorInsert into @TableName, @NoOfRows, @TableID

	while @@FETCH_STATUS=0 begin
		SET @proc = 'insert_' + CONVERT(VARCHAR(50), @TableName) + ' ' + CONVERT(VARCHAR(50), @NoOfRows)
		exec ('insert into TestRunTables values ('+ @TestID + ',' + @TableID + ',convert(varchar(50),GETDATE()),convert(varchar(50),GETDATE()))')
		exec ('UPDATE TestRunTables SET StartAt = GETDATE() WHERE TestRunID =' + @TestID + 'and TableID=' + @TableID)
		EXEC sp_executesql @proc
		exec ('UPDATE TestRunTables SET EndAt = GETDATE() WHERE TestRunID =' + @TestID + 'and TableID=' + @TableID)
		fetch next from CursorInsert into @TableName, @NoOfRows, @TableID
	end

	close CursorInsert
	deallocate CursorInsert

	--cursor view
	declare CursorView cursor forward_only

	for select Name, TV.ViewID
	from Views V inner join TestViews TV on V.ViewID=TV.ViewID
	where TV.TestID=@TestID

	open CursorView

	fetch next from CursorView into @ViewName, @ViewID

	while @@FETCH_STATUS=0 begin
		exec ('insert into TestRunViews values ('+ @TestID + ',' + @ViewID + ',convert(varchar(50),GETDATE()),convert(varchar(50),GETDATE()))')
		exec ('UPDATE TestRunViews SET StartAt = GETDATE() WHERE TestRunID =' + @TestID + 'and ViewID=' + @ViewID)
		exec ('SELECT * FROM ' + @ViewName)
		exec ('UPDATE TestRunViews SET EndAt = GETDATE() WHERE TestRunID =' + @TestID + 'and ViewID=' + @ViewID)
		fetch next from CursorView into @ViewName, @ViewID
	end

	close CursorView
	deallocate CursorView
	
	exec ('UPDATE TestRuns SET EndAt = GETDATE() WHERE Description =' + @TestID)

	fetch next from CursorTeste into @TestID
end

close CursorTeste
Deallocate CursorTeste

SELECT * FROM TestRuns
SELECT * FROM TestRunTables
SELECT * FROM TestRunViews