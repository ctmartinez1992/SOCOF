package body VehicleConflictInfo is

   task body VehicleConflictInfoTask is
      newCoord:		COORDINATE;
   begin
      loop
         delay 0.1;
         newCoord.Y := newCoord.Y + 0.4;
         objectCoordinate.write(newCoord);
      end loop;
   end VehicleConflictInfoTask;

end VehicleConflictInfo;
