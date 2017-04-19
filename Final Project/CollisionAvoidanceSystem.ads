with Math,
     Ada.Numerics.Elementary_Functions,
     Ada.Numerics.Generic_Elementary_Functions,
     Ada.Real_Time,
     Ada.Integer_Text_IO,
     Ada.Float_Text_IO,
     Ada.Text_IO;

use Math,
    Ada.Numerics.Elementary_Functions,
    Ada.Text_IO,
    Ada.Float_Text_IO;

package CollisionAvoidanceSystem is

   --Voltage � um valor entre 1 e 8
   type Voltage is range 1..8;

   --Coeficiente de fric��o
   type Friction is (DryAsphalt, WetAsphalt, DryConcrete, WetConcrete, Snow, Ice);
   --type Friction is (0.7, 0.55, 0.8, 0.5, 0.3, 0.15);

   f: 	Friction := DryAsphalt;

   --Coordenadas X e Y usadas no c�lculo da dist�ncia
   type COORDINATE is
      record
         X:  Float := 0.0;
         Y:  Float := 0.0;
      end record;

   --Protege o objeto COORDINATE
   protected type ProtectedCoordinate is
      procedure read(curCoord: out COORDINATE);
      procedure write(newCoord: in COORDINATE);
   private
      coord: COORDINATE;
   end ProtectedCoordinate;

   carCoordinate: ProtectedCoordinate;

   --Procedimentos para fazerem get aos valores de distancia e de speed
   procedure getSpeed(s: out Float);
   procedure getDistance(d: out Float);
   procedure updateDistance(d: in Float; yD: in Float);

   --stoppingDistance � a dist�ncia necess�ria para parar o carro totalmente, tendo em considera��o a velocidade e o coeficiente de fric��o do solo
   function getVoltage(Velocity: in Float; U: in Float; Dist: in Float) return Voltage;

   --Retorna o valor do coeficiente de fric��o
   function getFriction(fValue: in Friction) return Float;

   task CASUpdate is
      entry rotationSignal; 			--wheelShaft call this entry to signal the completion of 1 wheel shaft rotation
   end CASUpdate;

   task CAS is
   end CAS;

private
   --Velocidade a que o carro vai num determinado momento
   speed:	Float;

   --Dist�ncia para o carro da frente num determinado momento
   distance:	Float;

   --Dist�ncia dos ys para o carro da frente num determinado momento
   yDistance:	Float;

end CollisionAvoidanceSystem;
