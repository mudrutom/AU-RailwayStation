% all the node constants need to be different
fof(nodesAllDiff, axiom, (
   ((in1 != v1) & (in1 != out1) & (in1 != out2) & (v1 != out1) & (v1 != out2) & (out1 != out2))
)).

% transition axiom for input node in1
fof(node_in1, axiom, (
   (![X]: ![T]: (at(succ(X), T, in1) <=> ((enter(X, T, in1) & (![R]: ~at(X, R, in1))) | (at(X, T, in1) & (~goes(X, T) | ~open(X, in1))))))
)).
% transition axiom for inner node v1
fof(node_v1, axiom, (
   (![X]: ![T]: (at(succ(X), T, v1) <=> ((at(X, T, v1) & ~goes(X, T)) | (at(X, T, in1) & goes(X, T) & open(X, in1)))))
)).
% transition axiom for output node out1
fof(node_out1, axiom, (
   (![X]: ![T]: (at(succ(X), T, out1) <=> ((at(X, T, v1) & goes(X, T) & (switch(X, v1) = out1)))))
)).
% transition axiom for output node out2
fof(node_out2, axiom, (
   (![X]: ![T]: (at(succ(X), T, out2) <=> ((at(X, T, v1) & goes(X, T) & (switch(X, v1) = out2)))))
)).

% the gate function domain axiom
fof(gateDomain, axiom, (
   (![T]: ((gate(T) = out1) | (gate(T) = out2)))
)).
% the switch function domain axiom for v1
fof(switch_v1, axiom, (
   (![X]: ((switch(X, v1) = out1) | (switch(X, v1) = out2)))
)).

% no train can be at any two nodes in the same time
fof(singularTrainLocation, axiom, (
   (![X]: ![T]: ![N1]: ![N2]: ((at(X, T, N1) & at(X, T, N2)) => (N1 = N2)))
)).
% the train driver has to go eventually
fof(trainDriverGoes, axiom, (
   (![X]: ![T]: ![N]: (at(X, T, N) => (?[Y]: (less(X, Y) & goes(Y, T)))))
)).
