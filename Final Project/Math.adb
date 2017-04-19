package body Math is

   function Sqrt(A: Float; B: Integer) return Float is
      Root: float:=0.0;
   begin
      Root := A**(1.0/Float(B));
      return Root;
   end Sqrt;

   function Pow(X: Float; P: Integer) return Float is
      Val: float:=X;
      I: Integer:=1;
   begin
      Val:=X**P;
      --        loop
      --           exit when I=P;
      --           Val := Val*X;
      --           I:=I+1;
      --        end loop;
      return Val;
   end Pow;

end Math;
