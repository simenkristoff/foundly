FROM gitpod/workspace-full-vnc

USER gitpod

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
             && sdk install java 14.0.2.j9-adpt \
             && sdk default java 14.0.2.j9-adpt"

USER gitpod

ARG DEBIAN_FRONTEND=noninteractive

# Install Cypress dependencies.
RUN sudo apt-get update \
 && sudo apt-get install -yq \
   libnss3 \
 && sudo rm -rf /var/lib/apt/lists/*

USER gitpod

#Install Google key
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add - 
RUN sudo sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'

# Install custom tools, runtime, etc.
RUN sudo add-apt-repository -y ppa:neovim-ppa/unstable
RUN sudo apt-get update && \
    sudo apt-get install -y zsh neovim google-chrome-stable

