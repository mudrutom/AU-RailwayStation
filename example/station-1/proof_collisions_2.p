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
% the train driver always goes
fof(alwaysGoes, axiom, (
   (![X]: ![T]: goes(X, T))
)).

% no train collisions for v2
fof(noCollisions_v2, conjecture, (
   (![X]: ![T1]: ![T2]: (((T1 != T2) & at(X, T1, v2)) => ~at(X, T2, v2)))
)).
