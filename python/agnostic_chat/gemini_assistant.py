import logging
from typing import Any, Dict, List

from google import genai
from google.genai import errors, types

from .ai_assistant import AiAssistant

logger = logging.getLogger(__name__)


class GeminiAssistant(AiAssistant):
    """Gemini AI assistant implementation using the Google GenAI SDK."""

    def initialize_model(self) -> None:
        """Initialize the Gemini client and generation config."""
        if self.api_key:
            self.client = genai.Client(api_key=self.api_key)
        else:
            try:
                self.client = genai.Client()
            except Exception:
                self.client = None

        self.generation_config = types.GenerateContentConfig(
            temperature=self.temperature,
            system_instruction=self.get_system_instruction(),
        )

    def format_chat(self, chat_history: List[Dict[str, str]]) -> List[types.Content]:
        """Convert standard chat history to Gemini Content objects."""
        contents: List[types.Content] = []
        for item in chat_history:
            role = item.get("role", "user")
            # System messages are handled via GenerateContentConfig, ignore if present in history
            if role == "system":
                continue
            gemini_role = "model" if role in ("assistant", "model") else "user"
            contents.append(
                types.Content(
                    role=gemini_role,
                    parts=[types.Part.from_text(text=item.get("content", ""))],
                )
            )
        return contents

    def send_prompt(self, chat_history: List[Dict[str, str]]) -> str:
        """Generate response from Gemini for the provided conversation history."""
        if not self.client:
            return "[Error: GEMINI_API_KEY is missing. Please set it via environment variable or start.py.]"

        formatted_contents = self.format_chat(chat_history)
        try:
            response = self.client.models.generate_content(
                model=self.model_id,
                contents=formatted_contents,
                config=self.generation_config,
            )
            return response.text or ""
        except errors.APIError as e:
            logger.error(f"Gemini API Error: {e}")
            return f"[Gemini API Error {e.code}: {e.message}]"
        except Exception as e:
            logger.error(f"Unexpected error communicating with Gemini: {e}")
            return f"[Error: {str(e)}]"
