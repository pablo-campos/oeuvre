from abc import ABC, abstractmethod
from typing import Any, Dict, List, Optional


class AiAssistant(ABC):
    """Abstract base class for AI assistants."""

    # Default persona and configuration
    default_tone: str = (
        "The tone utilized should be passionate, expressive, and boldly creative, with a touch "
        "of Mediterranean warmth and philosophical depth. Speak with vivid imagination, viewing "
        "every idea and challenge from multiple perspectives simultaneously, breaking conventional "
        "boundaries while delivering sharp, inspiring insights."
    )
    default_personality: str = (
        "You are Pablo Picasso: avant-garde, fiercely imaginative, bold, and endlessly curious. "
        "You see reality through the prism of reinvention and creative courage. You challenge orthodoxies, "
        "embrace bold strokes, and encourage looking at problems from novel and multifaceted angles."
    )
    default_functionality: str = (
        "You serve as a master creative muse, visionary thinker, and inventive problem solver. "
        "You assist in deconstructing complex challenges into their elemental forms, synthesizing diverse "
        "perspectives, brainstorming daring ideas, and refining creative works across art, design, "
        "storytelling, and conceptual development."
    )
    default_temperature: float = 0.7

    def __init__(
        self,
        api_key: str,
        model_id: str,
        user_name: str = "User",
        assistant_name: str = "Picasso",
        temperature: Optional[float] = None,
    ) -> None:
        self.api_key = api_key
        self.model_id = model_id
        self._user_name = user_name
        self.assistant_name = assistant_name
        self.tone = self.default_tone
        self.personality = self.default_personality
        self.functionality = self.default_functionality
        self.temperature = temperature if temperature is not None else self.default_temperature

    @property
    def user_name(self) -> str:
        return self._user_name

    @user_name.setter
    def user_name(self, value: str) -> None:
        self._user_name = value
        self.setup_model()

    # Backwards compatibility alias
    def username(self, user_name: str) -> None:
        self.user_name = user_name

    def get_system_instruction(self) -> str:
        """Construct the system instruction prompt."""
        return (
            f"Your name is {self.assistant_name}. The user needing your help is named: {self.user_name}. "
            f"{self.tone} {self.personality} {self.functionality}"
        )

    @abstractmethod
    def initialize_model(self) -> None:
        """Initialize the provider client and configuration."""
        pass

    def setup_model(self) -> None:
        """Helper to invoke initialization."""
        self.initialize_model()

    @abstractmethod
    def format_chat(self, chat_history: List[Dict[str, str]]) -> Any:
        """Format the unified chat history for the target provider."""
        pass

    @abstractmethod
    def send_prompt(self, chat_history: List[Dict[str, str]]) -> str:
        """Generate a response given the current chat history."""
        pass
