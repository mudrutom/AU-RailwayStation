include('order.p').
include('layout.p').
include('control.p').

% ADDITIONAL AXIOMS :

% the train always enters for in1
fof(alwaysEnters_in1, axiom, (
   (![X]: ![T]: enter(X, T, in1))
)).
% the train always enters for in2
fof(alwaysEnters_in2, axiom, (
   (![X]: ![T]: enter(X, T, in2))
)).
% the train always enters for in3
fof(alwaysEnters_in3, axiom, (
   (![X]: ![T]: enter(X, T, in3))
)).
% the train driver always goes
fof(alwaysGoes, axiom, (
   (![X]: ![T]: goes(X, T))
)).

% no train collisions for s2
fof(noCollisions_s2, conjecture, (
   (![X]: ![T1]: ![T2]: (((T1 != T2) & at(X, T1, s2)) => ~at(X, T2, s2)))
)).
