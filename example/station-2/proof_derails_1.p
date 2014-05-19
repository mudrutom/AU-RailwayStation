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

% no train derails for s7
fof(noDerails_s7, conjecture, (
   (![X]: ![T]: ((at(X, T, s7) & at(succ(X), T, s7)) => (switch(X, s7) = switch(succ(X), s7))))
)).
