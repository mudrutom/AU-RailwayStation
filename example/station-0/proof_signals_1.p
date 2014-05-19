include('order.p').
include('layout.p').
include('control.p').

% ADDITIONAL AXIOMS :

% the train always enters for in1
fof(alwaysEnters_in1, axiom, (
   (![X]: ![T]: enter(X, T, in1))
)).
% the train driver always goes
fof(alwaysGoes, axiom, (
   (![X]: ![T]: goes(X, T))
)).

% the signal opens for in1
fof(signalOpens_in1, conjecture, (
   (?[X]: open(X, in1))
)).
