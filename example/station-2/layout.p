% all the node constants need to be different
fof(nodesAllDiff, axiom, (
   ((in1 != s7) & (in1 != in2) & (in1 != s4) & (in1 != in3) & (in1 != s5) & (in1 != s2) & (in1 != s3) & (in1 != out1) & (in1 != out2) & (in1 != out3) & (s7 != in2) & (s7 != s4) & (s7 != in3) & (s7 != s5) & (s7 != s2) & (s7 != s3) & (s7 != out1) & (s7 != out2) & (s7 != out3) & (in2 != s4) & (in2 != in3) & (in2 != s5) & (in2 != s2) & (in2 != s3) & (in2 != out1) & (in2 != out2) & (in2 != out3) & (s4 != in3) & (s4 != s5) & (s4 != s2) & (s4 != s3) & (s4 != out1) & (s4 != out2) & (s4 != out3) & (in3 != s5) & (in3 != s2) & (in3 != s3) & (in3 != out1) & (in3 != out2) & (in3 != out3) & (s5 != s2) & (s5 != s3) & (s5 != out1) & (s5 != out2) & (s5 != out3) & (s2 != s3) & (s2 != out1) & (s2 != out2) & (s2 != out3) & (s3 != out1) & (s3 != out2) & (s3 != out3) & (out1 != out2) & (out1 != out3) & (out2 != out3))
)).

% transition axiom for input node in1
fof(node_in1, axiom, (
   (![X]: ![T]: (at(succ(X), T, in1) <=> ((enter(X, T, in1) & (![R]: ~at(X, R, in1))) | (at(X, T, in1) & (~goes(X, T) | ~open(X, in1))))))
)).
% transition axiom for inner node s7
fof(node_s7, axiom, (
   (![X]: ![T]: (at(succ(X), T, s7) <=> ((at(X, T, s7) & ~goes(X, T)) | (at(X, T, in1) & goes(X, T) & open(X, in1)))))
)).
% transition axiom for input node in2
fof(node_in2, axiom, (
   (![X]: ![T]: (at(succ(X), T, in2) <=> ((enter(X, T, in2) & (![R]: ~at(X, R, in2))) | (at(X, T, in2) & (~goes(X, T) | ~open(X, in2))))))
)).
% transition axiom for inner node s4
fof(node_s4, axiom, (
   (![X]: ![T]: (at(succ(X), T, s4) <=> ((at(X, T, s4) & ~goes(X, T)) | (at(X, T, in2) & goes(X, T) & open(X, in2)))))
)).
% transition axiom for input node in3
fof(node_in3, axiom, (
   (![X]: ![T]: (at(succ(X), T, in3) <=> ((enter(X, T, in3) & (![R]: ~at(X, R, in3))) | (at(X, T, in3) & (~goes(X, T) | ~open(X, in3))))))
)).
% transition axiom for inner node s5
fof(node_s5, axiom, (
   (![X]: ![T]: (at(succ(X), T, s5) <=> ((at(X, T, s5) & ~goes(X, T)) | (at(X, T, in3) & goes(X, T) & open(X, in3)))))
)).
% transition axiom for inner node s2
fof(node_s2, axiom, (
   (![X]: ![T]: (at(succ(X), T, s2) <=> ((at(X, T, s2) & ~goes(X, T)) | (at(X, T, s7) & goes(X, T) & (switch(X, s7) = s2)) | (at(X, T, s4) & goes(X, T) & (switch(X, s4) = s2)) | (at(X, T, s5) & goes(X, T) & (switch(X, s5) = s2)))))
)).
% transition axiom for inner node s3
fof(node_s3, axiom, (
   (![X]: ![T]: (at(succ(X), T, s3) <=> ((at(X, T, s3) & ~goes(X, T)) | (at(X, T, s7) & goes(X, T) & (switch(X, s7) = s3)) | (at(X, T, s4) & goes(X, T) & (switch(X, s4) = s3)) | (at(X, T, s5) & goes(X, T) & (switch(X, s5) = s3)))))
)).
% transition axiom for output node out1
fof(node_out1, axiom, (
   (![X]: ![T]: (at(succ(X), T, out1) <=> ((at(X, T, s2) & goes(X, T)) | (at(X, T, s3) & goes(X, T) & (switch(X, s3) = out1)))))
)).
% transition axiom for output node out2
fof(node_out2, axiom, (
   (![X]: ![T]: (at(succ(X), T, out2) <=> ((at(X, T, s4) & goes(X, T) & (switch(X, s4) = out2)) | (at(X, T, s5) & goes(X, T) & (switch(X, s5) = out2)) | (at(X, T, s3) & goes(X, T) & (switch(X, s3) = out2)))))
)).
% transition axiom for output node out3
fof(node_out3, axiom, (
   (![X]: ![T]: (at(succ(X), T, out3) <=> ((at(X, T, s3) & goes(X, T) & (switch(X, s3) = out3)))))
)).

% the gate function domain axiom
fof(gateDomain, axiom, (
   (![T]: ((gate(T) = out1) | (gate(T) = out2) | (gate(T) = out3)))
)).
% the switch function domain axiom for s7
fof(switch_s7, axiom, (
   (![X]: ((switch(X, s7) = s2) | (switch(X, s7) = s3)))
)).
% the switch function domain axiom for s4
fof(switch_s4, axiom, (
   (![X]: ((switch(X, s4) = s3) | (switch(X, s4) = s2) | (switch(X, s4) = out2)))
)).
% the switch function domain axiom for s5
fof(switch_s5, axiom, (
   (![X]: ((switch(X, s5) = s3) | (switch(X, s5) = s2) | (switch(X, s5) = out2)))
)).
% the switch function domain axiom for s3
fof(switch_s3, axiom, (
   (![X]: ((switch(X, s3) = out1) | (switch(X, s3) = out2) | (switch(X, s3) = out3)))
)).

% no train can be at any two nodes in the same time
fof(singularTrainLocation, axiom, (
   (![X]: ![T]: ![N1]: ![N2]: ((at(X, T, N1) & at(X, T, N2)) => (N1 = N2)))
)).
% the train driver has to go eventually
fof(trainDriverGoes, axiom, (
   (![X]: ![T]: ![N]: (at(X, T, N) => (?[Y]: (less(X, Y) & goes(Y, T)))))
)).