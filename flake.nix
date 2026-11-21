{
  description = "Replication Addon Tiers - dev shell";

  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";

  outputs = { self, nixpkgs }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};
    in {
      devShells.${system}.default = pkgs.mkShell {
        buildInputs = [ pkgs.jdk21 ];

        shellHook = ''
          export JAVA_HOME=${pkgs.jdk21}/lib/openjdk
        '';
      };
    };
}
